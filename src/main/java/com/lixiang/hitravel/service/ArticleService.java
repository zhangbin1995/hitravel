package com.lixiang.hitravel.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lixiang.hitravel.domain.Article;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.dto.GoodsForArticleDto;
import com.lixiang.hitravel.result.Result;

/**
 * @author zhang
 * @date 2020-01-16
 */
public interface ArticleService {

    // 添加攻略
    Result save(Article article);

    // 删除攻略
    Result delete(Integer articleId);

    // 审核攻略（管理员）
    Result passVerify(Integer articleId);

    // 修改攻略
    Result updateArticle(Article article);

    // 分页查询攻略
    Result findArticleByPage(IPage<Article> page, QueryWrapper<Article> wrapper);

    // 分页查询我的攻略
    Result queryMyArticle(Integer status, Integer pageNo, Integer pageSize, Integer userId);

    // 点赞攻略
    Result praise(Integer articleId);

    // 评论攻略
    Result comment(User user, Integer articleId, String content);

    // 查询某攻略下所有评论
    Result queryCommentByArticleId(Integer articleId, Integer pageNo, Integer pageSize);

    // 收藏攻略
    Result collection(User user, Integer articleId);

    // 我收藏的攻略
    Result myCollection(Integer userId);

    // 分页查询攻略文章
    Result queryArticleByPage(String cityName, Integer type, Integer status, Integer pageNo, Integer pageSize);

    // 查看某一篇攻略
    Result queryArticleById(Integer articleId);

    // 删除攻略
    Result deleteById(Integer articleId);

    // 关键字搜索
    Result queryArticleByKeyWords(String keyWords, Integer pageNo, Integer pageSize);

    // 添加攻略相关商品
    Result addGoods(User user, GoodsForArticleDto goodsForArticleDto);

    // 查看某攻略下的商品
    Result queryArticleGoodsById(Integer articleId);
}
