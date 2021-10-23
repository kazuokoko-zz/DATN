package com.poly.datn.service.impl;

import com.poly.datn.dao.BlogDAO;
import com.poly.datn.dao.BlogDetailsDAO;
import com.poly.datn.entity.Blog;
import com.poly.datn.entity.BlogDetails;
import com.poly.datn.service.BlogService;
import com.poly.datn.service.CommentService;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.BlogDetailsVO;
import com.poly.datn.vo.BlogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    BlogDetailsDAO blogDetailsDAO;

    @Autowired
    CommentService commentService;

    @Override
    public Object getById(Integer id) {
        Blog blog = blogDAO.findById(id).orElseThrow(() -> new NullPointerException("Blog not found"));
        BlogVO blogVO = new BlogVO();
        BeanUtils.copyProperties(blog, blogVO);
        blogVO.setTimeCreated(sdf.format(blog.getTimeCreated()));
        List<BlogDetailsVO> blogDetailsVOS = new ArrayList<>();
        for (BlogDetails blogDetails : blogDetailsDAO.findByBlogId(blogVO.getId())) {
            BlogDetailsVO blogDetailsVO = new BlogDetailsVO();
            BeanUtils.copyProperties(blogDetails, blogDetailsVO);
            blogDetailsVOS.add(blogDetailsVO);
        }
        blogVO.setBlogDetails(blogDetailsVOS);
        blogVO.setComments(commentService.getListByBlogId(blogVO.getId()));
        return blogVO;
    }

    @Override
    public Object getList(Optional<Integer> pid, Optional<String> title) {
        List<Blog> blogs;
        if (pid.isPresent() && title.isPresent()) {
            blogs = blogDAO.findAllByProductIdEquals(pid.get());
            blogs = StringFind.getMatchBlog(blogs, title.get());

        } else if (pid.isPresent()) {
            blogs = blogDAO.findAllByProductIdEquals(pid.get());
        } else if (title.isPresent()) {
            blogs = blogDAO.findAll();
            blogs = StringFind.getMatchBlog(blogs, title.get());

        } else {
            blogs = blogDAO.findAll();
        }

        List<BlogVO> blogVOS = new ArrayList<>();
        blogs.forEach(blog -> {
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog, blogVO);
            blogVO.setTimeCreated(sdf.format(blog.getTimeCreated()));
            blogVOS.add(blogVO);
        });
        return blogVOS;
    }


}
