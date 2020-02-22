package com.lixiang.hitravel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lixiang.hitravel.domain.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author binzhang
 * @date 2020-02-17
 */
public interface ArticleMapper extends BaseMapper<Article> {

    @Select("select * from t_article a where a.article_id in (select r.article_id from t_user_article_ref r where r.user_id = #{userId})")
    List<Article> queryMyCollection(Integer userId);
}
