package com.poly.datn.service.impl;

import com.poly.datn.dao.AccountDAO;
import com.poly.datn.dao.BlogDAO;
import com.poly.datn.dao.BlogDetailsDAO;
import com.poly.datn.entity.Blog;
import com.poly.datn.entity.BlogDetails;
import com.poly.datn.service.BlogService;
import com.poly.datn.service.CommentService;
import com.poly.datn.utils.CheckRole;
import com.poly.datn.utils.StringFind;
import com.poly.datn.vo.BlogDetailsVO;
import com.poly.datn.vo.BlogVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa");

    @Autowired
    private BlogDAO blogDAO;

    @Autowired
    BlogDetailsDAO blogDetailsDAO;

    @Autowired
    CommentService commentService;

    @Autowired
    StringFind stringFind;

    @Autowired
    CheckRole checkRole;

    @Autowired
    AccountDAO accountDAO;

    @Override
    public Object getById(Integer id) {
        Blog blog = blogDAO.findById(id).orElseThrow(() -> new NullPointerException("Blog not found"));
        return getBlog(blog);
    }

    @Override
    public Object getList(Optional<Integer> pid, Optional<String> title) {
        List<Blog> blogs;
        if (pid.isPresent() && title.isPresent()) {
            blogs = blogDAO.findAllByProductIdEquals(pid.get());
            blogs = stringFind.getMatchBlog(blogs, title.get());

        } else if (pid.isPresent()) {
            blogs = blogDAO.findAllByProductIdEquals(pid.get());
        } else if (title.isPresent()) {
            blogs = blogDAO.findAll();
            blogs = stringFind.getMatchBlog(blogs, title.get());

        } else {
            blogs = blogDAO.findAll();
        }

        List<BlogVO> blogVOS = new ArrayList<>();
        for (Blog blog : blogs) {
            if (blog.getType() == 1) {
                continue;
            }
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog, blogVO);
            blogVO.setTimeCreated(sdf.format(blog.getTimeCreated()));
            blogVOS.add(blogVO);
        }
        return blogVOS;
    }

    @Override
    public BlogVO getABlog(Integer id, Principal principal) {
        if (principal == null) return null;
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        Blog blog = blogDAO.findById(id).orElseThrow(() -> new NullPointerException("Blog not found"));
        return getBlog(blog);
    }

    @Override
    public List<BlogVO> getListAdmin(Optional<Integer> pid, Optional<String> title, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        List<Blog> blogs = blogDAO.findAll();
        List<BlogVO> blogVOS = new ArrayList<>();
        for (Blog blog : blogs) {
            Boolean pidok = true;
            Boolean titleok = true;
            if (pid.isPresent()) {
                if (blog.getProductId() != null)
                    pidok = pid.get().equals(blog.getProductId());
            }
            if (title.isPresent()) {
                titleok = stringFind.checkContains(blog.getTitle(), title.get());
            }
            BlogVO blogVO = getBlog(blog);
            if (pidok || titleok) {
                blogVOS.add(blogVO);
            }
        }
        return blogVOS;
    }

    @Override
    public Boolean deleteById(Optional<Integer> id, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff")) ||
                !id.isPresent()) {
            return null;
        }
        try {
            Blog blog = blogDAO.getById(id.get());
            blog.setStatus(false);
            blogDAO.save(blog);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public BlogVO create(BlogVO blogVO, Principal principal) {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            return null;
        }
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVO, blog);
        blog.setTimeCreated(Timestamp.valueOf(LocalDateTime.now()));
        blog.setCreatedBy(accountDAO.findAccountByUsername(principal.getName()).getId());
        if (blog.getProductId() != null) {
            blog.setType(1);
        } else {
            blog.setType(2);
        }
        blog = blogDAO.save(blog);
        List<BlogDetailsVO> blogDetails = blogVO.getBlogDetails();
        List<BlogDetails> blogDetails1 = new ArrayList<>();
        for (BlogDetailsVO blogDetailsVO: blogDetails
             ) {
            BlogDetails blogDetailsVO1 = new BlogDetails();
            BeanUtils.copyProperties(blogDetails1, blogDetailsVO1);
            blogDetails1.add(blogDetailsVO1);
        }
        blogDetails1 = blogDetailsDAO.saveAll(blogDetails1);
        blog.setBlogDetails(blogDetails1);
        BeanUtils.copyProperties(blog, blogVO);
        return blogVO;
    }

    @Override
    public BlogVO update(BlogVO blogVO, Optional<Integer> id, Principal principal) throws ParseException {
        if (principal == null) {
            return null;
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff")) ||
                !id.isPresent()) {
            return null;
        }
        if (id.get() != blogVO.getId()) {
            return null;
        }
        List<BlogDetails> blogDetails = blogDetailsDAO.findByBlogId(id.get());
        for (BlogDetails details : blogDetails) {
            blogDetailsDAO.deleteById(details.getId());
        }
        for (BlogDetailsVO blogDetailsVO : blogVO.getBlogDetails()) {
            BlogDetails details = new BlogDetails();
            BeanUtils.copyProperties(blogDetailsVO, details);
            details.setBlogId(blogVO.getId());
            blogDetailsDAO.save(details);
        }
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVO, blog);
        blog.setTimeCreated(Timestamp.valueOf(sdf.parse(blogVO.getTimeCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
        blog = blogDAO.save(blog);

        BeanUtils.copyProperties(blog, blogVO);
        List<BlogDetailsVO> blogDetailsVOS = new ArrayList<>();
        for (BlogDetails details : blogDetailsDAO.findByBlogId(blog.getId())) {
            BlogDetailsVO blogDetailsVO = new BlogDetailsVO();
            BeanUtils.copyProperties(details, blogDetailsVO);
            blogDetailsVOS.add(blogDetailsVO);
        }
        blogVO.setBlogDetails(blogDetailsVOS);
        blogVO.setComments(commentService.getListByBlogId(blog.getId()));
        return blogVO;
    }

    private BlogVO getBlog(Blog blog) {
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

}
