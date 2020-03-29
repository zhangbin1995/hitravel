package com.lixiang.hitravel.controller;

import com.lixiang.hitravel.domain.Article;
import com.lixiang.hitravel.domain.User;
import com.lixiang.hitravel.dto.ArticleDto;
import com.lixiang.hitravel.redis.RedisService;
import com.lixiang.hitravel.redis.UserKey;
import com.lixiang.hitravel.result.CodeMsg;
import com.lixiang.hitravel.result.Result;
import com.lixiang.hitravel.service.ArticleService;
import com.lixiang.hitravel.util.MyBeanUtils;
import com.lixiang.hitravel.util.WebLog;
import com.lixiang.hitravel.validator.UserLoginToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author binzhang
 * @date 2020-02-17
 */
@RestController
@Api(value = "攻略管理", tags = "攻略管理")
@RequestMapping(value = "/article")
@SuppressWarnings("all")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "新增攻略", notes = "新增攻略")
    @PostMapping(value = "/add")
    @UserLoginToken
    public Result add(HttpServletRequest request, @Valid @RequestBody ArticleDto articleDto) {
        Article article = MyBeanUtils.dtoToVo(articleDto, Article.class);
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "当前用户为空");
        }
        article.setUserId(user.getUserId());
        article.setUsername(user.getUsername());
        return articleService.save(article);
    }

    @ApiOperation(value = "修改攻略", notes = "修改攻略")
    @PostMapping(value = "/update")
    @UserLoginToken
    public Result update(@Valid @RequestBody ArticleDto articleDto) {
        Article article = MyBeanUtils.dtoToVo(articleDto, Article.class);
        return articleService.updateArticle(article);
    }

    @ApiOperation(value = "删除攻略", notes = "删除攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id")
    })
    @GetMapping(value = "/deleteById")
    public Result deleteById(@RequestParam Integer articleId) {
        return articleService.deleteById(articleId);
    }

    @ApiOperation(value = "查看某一篇攻略", notes = "查看某一篇攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id")
    })
    @GetMapping(value = "/queryArticleById")
//    @UserLoginToken
    public Result queryArticleById(@RequestParam Integer articleId) {
        return articleService.queryArticleById(articleId);
    }

    @ApiOperation(value = "我发表的攻略（游客）", notes = "我发表的攻略（游客）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态", paramType = "query", dataType = "Integer", defaultValue = "状态 0 未审核 1 审核 不传默认全部"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @GetMapping(value = "/queryMyArticle")
    @UserLoginToken
    public Result queryMyArticle(HttpServletRequest request, @RequestParam(required = false) Integer status, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "当前用户为空");
        }
        return articleService.queryMyArticle(status, pageNo, pageSize, user.getUserId());
    }

    @ApiOperation(value = "审核攻略（管理员）", notes = "审核攻略（管理员）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id")
    })
    @GetMapping(value = "/passVerify")
    @UserLoginToken
    public Result passVerify(@RequestParam Integer articleId) {
        return articleService.passVerify(articleId);
    }

    @ApiOperation(value = "点赞攻略", notes = "点赞攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id")
    })
    @GetMapping(value = "/praise")
    @UserLoginToken
    public Result praise(@RequestParam Integer articleId) {
        return articleService.praise(articleId);
    }

    @ApiOperation(value = "评论攻略", notes = "评论攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id"),
            @ApiImplicitParam(name = "content", value = "评论内容", paramType = "query", dataType = "String", defaultValue = "评论内容")
    })
    @GetMapping(value = "/comment")
    @UserLoginToken
    public Result comment(HttpServletRequest request, @RequestParam Integer articleId, @RequestParam String content) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "当前用户为空");
        }
        return articleService.comment(user, articleId, content);
    }

    @ApiOperation(value = "查看该攻略下的所有评论", notes = "查看该攻略下的所有评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @GetMapping(value = "/queryCommentByArticleId")
    @UserLoginToken
    public Result queryCommentByArticleId(@RequestParam Integer articleId, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return articleService.queryCommentByArticleId(articleId, pageNo, pageSize);
    }

    @ApiOperation(value = "收藏攻略", notes = "收藏攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "攻略id", paramType = "query", dataType = "String", defaultValue = "攻略id")
    })
    @GetMapping(value = "/collection")
    @UserLoginToken
    public Result collection(HttpServletRequest request, @RequestParam Integer articleId) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "当前用户为空");
        }
        return articleService.collection(user, articleId);
    }

    @ApiOperation(value = "我收藏的攻略", notes = "我收藏的攻略")
    @GetMapping(value = "/myCollection")
    @UserLoginToken
    public Result myCollection(HttpServletRequest request) {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        User user = redisService.get(UserKey.token, token, User.class);
        if (user == null) {
            return Result.error(CodeMsg.ERROR, "当前用户为空");
        }
        return articleService.myCollection(user.getUserId());
    }

    @ApiOperation(value = "分页查询攻略", notes = "分页查询攻略")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cityName", value = "城市名称（非必传）", paramType = "query", dataType = "String", defaultValue = "城市名称（非必传）"),
            @ApiImplicitParam(name = "type", value = "查询类型（0-最新 1-点赞数）", paramType = "query", dataType = "Integer", defaultValue = "查询类型（0-最新 1-点赞数）"),
            @ApiImplicitParam(name = "status", value = "审核状态（游客传1，表示已审核的攻略，不传全部攻略，0表示未审核攻略）", paramType = "query", dataType = "Integer", defaultValue = "审核状态（游客传1，表示已审核的攻略，不传全部攻略，0表示未审核攻略）"),
            @ApiImplicitParam(name = "pageNo", value = "页码", required = true, paramType = "query", dataType = "Integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页记录数", required = true, paramType = "query", dataType = "Integer", defaultValue = "10")
    })
    @WebLog
    @GetMapping(value = "/queryArticleByPage")
//    @UserLoginToken
    public Result queryArticleByPage(@RequestParam(required = false) String cityName, @RequestParam Integer type, @RequestParam(required = false) Integer status, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        return articleService.queryArticleByPage(cityName, type, status, pageNo, pageSize);
    }

}
