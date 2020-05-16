package com.lixiang.hitravel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Strings;
import com.lixiang.hitravel.domain.*;
import com.lixiang.hitravel.dto.GoodsDto;
import com.lixiang.hitravel.dto.GoodsForArticleDto;
import com.lixiang.hitravel.exception.GlobalException;
import com.lixiang.hitravel.mapper.*;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhang
 * @date 2020-02-17
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserArticleRefMapper userArticleRefMapper;

    @Autowired
    private ArticleGoodsRefMapper articleGoodsRefMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private TicketMapper ticketMapper;

    @Override
    public Result save(Article article) {
        try {
            article.setCollection(0);
            article.setPraise(0);
            article.setView(0);
            article.setStatus(0);
            article.setCreateTime(new Date());
            int res = articleMapper.insert(article);
            if (res > 0) {
                return Result.success(article);
            } else {
                return Result.error(CodeMsg.ERROR, "添加失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result delete(Integer articleId) {
        return null;
    }

    @Override
    public Result passVerify(Integer articleId) {
        try {
            Article article = articleMapper.selectById(articleId);
            article.setStatus(1);
            int res = articleMapper.updateById(article);
            if (res > 0) {
                return Result.success(article);
            } else {
                return Result.error(CodeMsg.ERROR, "审核攻略失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result updateArticle(Article article) {
        try {
            int res = articleMapper.updateById(article);
            if (res > 0) {
                return Result.success("修改成功");
            } else {
                return Result.error(CodeMsg.ERROR, "修改失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result findArticleByPage(IPage<Article> page, QueryWrapper<Article> wrapper) {
        return null;
    }

    @Override
    public Result queryMyArticle(Integer status, Integer pageNo, Integer pageSize, Integer userId) {
        try {
            IPage<Article> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            Article temp = new Article();
            temp.setUserId(userId);
            if (status != null) {
                temp.setStatus(status);
            }
            wrapper.setEntity(temp);
            return Result.success(articleMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result praise(Integer articleId) {
        try {
            Article article = articleMapper.selectById(articleId);
            if (article == null) {
                return Result.error(CodeMsg.ERROR, "攻略不存在");
            }
            article.setPraise(article.getPraise()+1);
            int res = articleMapper.updateById(article);
            if (res > 0) {
                return Result.success("点赞成功，当前点赞数：" + article.getPraise());
            } else {
                return Result.error(CodeMsg.ERROR, "点赞失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result comment(User user, Integer articleId, String content) {
        try {
            Comment comment = new Comment();
            comment.setArticleId(articleId);
            comment.setContent(content);
            comment.setUserId(user.getUserId());
            comment.setUsername(user.getUsername());
            comment.setCreateTime(new Date());
            int res = commentMapper.insert(comment);
            if (res > 0) {
                return Result.success("评论成功");
            } else {
                return Result.error(CodeMsg.ERROR, "评论失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result queryCommentByArticleId(Integer articleId, Integer pageNo, Integer pageSize) {
        try {
            IPage<Comment> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Comment> wrapper = new QueryWrapper<>();
            Comment temp = new Comment();
            temp.setArticleId(articleId);
            wrapper.setEntity(temp);
            return Result.success(commentMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result collection(User user, Integer articleId) {
        try {
            Article article = articleMapper.selectById(articleId);
            article.setCollection(article.getCollection()+1);
            articleMapper.updateById(article);
            UserArticleRef ref = new UserArticleRef();
            ref.setArticleId(articleId);
            ref.setUserId(user.getUserId());
            int res = userArticleRefMapper.insert(ref);
            if (res > 0) {
                return Result.success("收藏成功，当前收藏数：" + article.getCollection());
            } else {
                return Result.error(CodeMsg.ERROR, "收藏失败");
            }
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result myCollection(Integer userId) {
        try {
            List<Article> list = articleMapper.queryMyCollection(userId);
            if (list != null || list.size() > 0) {
                return Result.success(list);
            }
            return Result.success("该用户尚未收藏攻略");
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result queryArticleByPage(String cityName, Integer type, Integer status, Integer pageNo, Integer pageSize) {
        try {
            IPage<Article> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            Article temp = new Article();
            if (!Strings.isNullOrEmpty(cityName)) {
                temp.setCityName(cityName);
            }
            if (status != null) {
                temp.setStatus(status);
            }
            wrapper.setEntity(temp);
            List<Article> list = null;
            if (type == 0) {
                // 按时间倒序输出
                wrapper.orderByDesc("create_time");
            } else if (type == 1){
                wrapper.orderByDesc("praise");
            }
            return Result.success(articleMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result queryArticleById(Integer articleId) {
        try {
            return Result.success(articleMapper.selectById(articleId));
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result deleteById(Integer articleId) {
        try {
            int res = articleMapper.deleteById(articleId);
            if (res > 0) {
                return Result.success("删除成功");
            }
            return Result.error(CodeMsg.ERROR, "删除失败");
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result queryArticleByKeyWords(String keyWords, Integer pageNo, Integer pageSize) {
        try {
            IPage<Article> page = new Page<>(pageNo, pageSize);
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.like("city_name", keyWords).or().like("title", keyWords).or().like("content", keyWords);
            wrapper.orderByDesc("create_time");
            return Result.success(articleMapper.selectPage(page, wrapper));
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Result addGoods(User user, GoodsForArticleDto goodsForArticleDto) {
        try {
            Integer articleId = goodsForArticleDto.getArticleId();
            Article article = articleMapper.selectById(articleId);
            if (article.getUserId() != user.getUserId()) {
                throw new GlobalException("仅有作者可添加攻略商品！");
            }
            List<GoodsDto> goodsList = goodsForArticleDto.getGoodsList();
            for (GoodsDto goodsDto : goodsList) {
                ArticleGoods goods = new ArticleGoods();
                goods.setArticleId(articleId);
                goods.setGoodsId(goodsDto.getId());
                goods.setType(goodsDto.getType());
                // 根据类型判断预订的是票或酒店 0酒店 1景区
                if (goodsDto.getType() == 0) {
                    // 商品为酒店房间
                    Room room = roomMapper.selectById(goodsDto.getId());
                    if (room == null) {
                        throw new GlobalException("查询房间信息错误!");
                    }
                    goods.setHotelOrScenicName(room.getHotelName());
                    goods.setRoomOrTicketName(room.getRoomName());
                    goods.setNormalPrice(room.getNormalPrice());
                    goods.setSellPrice(room.getSellPrice());

                } else {
                    // 商品为景区门票
                    Ticket ticket = ticketMapper.selectById(goodsDto.getId());
                    if (ticket == null) {
                        throw new GlobalException("查询门票信息错误!");
                    }
                    goods.setHotelOrScenicName(ticket.getScenicName());
                    goods.setRoomOrTicketName(ticket.getTicketName());
                    goods.setNormalPrice(ticket.getNormalPrice());
                    goods.setSellPrice(ticket.getSellPrice());
                }

                int res = articleGoodsRefMapper.insert(goods);
                if (res < 1) {
                    throw new GlobalException("添加记录失败");
                }
            }

            return Result.success("添加成功");
        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }

    @Override
    public Result queryArticleGoodsById(Integer articleId) {
        try {
            QueryWrapper<ArticleGoods> wrapper = new QueryWrapper<>();
            ArticleGoods temp = new ArticleGoods();
            temp.setArticleId(articleId);
            wrapper.setEntity(temp);
            return Result.success(articleGoodsRefMapper.selectList(wrapper));

        } catch (GlobalException e) {
            throw new GlobalException(e.getMessage());
        }
    }
}
