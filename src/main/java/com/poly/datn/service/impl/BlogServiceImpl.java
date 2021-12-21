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
import org.apache.commons.lang.NotImplementedException;
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
import java.util.*;

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
        Blog blog = blogDAO.getOneByIdAndStatus(id);
        if (blog == null || blog.getType() == 1 || blog.getStatus().equals(false)) {
            throw new NotImplementedException("Không có blog này");
        }
        return getBlog(blog);
    }

    @Override
    public Object getOneByIdAdmin(Integer id, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");

        }
        Blog blog = blogDAO.getOneByIdAndStatusAdmin(id);
        if (blog == null) {
            throw new NotImplementedException("Không có blog này");
        }
        return getBlog(blog);
    }

    @Override
    public Object getList(Optional<Integer> pid, Optional<String> title) {
        List<Blog> blogs;
        Boolean status = true;
        if (pid.isPresent() && title.isPresent()) {
            blogs = blogDAO.findAllByProductIdEqualsAndStatus(pid.get(), status);
            blogs = stringFind.getMatchBlog(blogs, title.get());
        } else if (pid.isPresent()) {
            blogs = blogDAO.findAllByProductIdEqualsAndStatus(pid.get(), status);
        } else if (title.isPresent()) {
            blogs = blogDAO.getAllByStatus(status);
            blogs = stringFind.getMatchBlog(blogs, title.get());
        } else {
            blogs = blogDAO.getAllByStatus(status);
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
        Collections.sort(blogVOS, Comparator.comparing(BlogVO::getTimeCreated).reversed());
        return blogVOS;
    }

    @Override
    public BlogVO getABlog(Integer id, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");

        }
        Blog blog = blogDAO.findById(id).orElseThrow(() -> new NotImplementedException("Chưa truyền id"));
        return getBlog(blog);
    }

    @Override
    public List<BlogVO> getListAdmin(Optional<Integer> pid, Optional<String> title, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
        Boolean status = true;
        List<Blog> blogs = blogDAO.getAllByStatus(status);
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
        Collections.sort(blogVOS, Comparator.comparing(BlogVO::getTimeCreated).reversed());
        return blogVOS;
    }

    @Override
    public List<BlogVO> getListDeleteAdmin(Optional<Integer> pid, Optional<String> title, Principal principal) {
        if (principal == null) {
            throw new NotImplementedException("Chưa đăng nhập");
        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
        }
        Boolean status = false;
        List<Blog> blogs = blogDAO.getAllByStatus(status);
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
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff")) ||
                !id.isPresent()) {
            throw new NotImplementedException("User này không có quyền");

        }
        try {
            Blog blog = blogDAO.getById(id.get());
            if (blog == null || blog.getType() == 1 || blog.getStatus().equals(false)) {
                throw new NotImplementedException("Không tồn tại blog này");
            }
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
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff"))) {
            throw new NotImplementedException("User này không có quyền");
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
        blog.setStatus(true);
        List<BlogDetailsVO> blogDetails = blogVO.getBlogDetails();
        if (blogDetails.size() <= 0) {
            blog.setStatus(false);
        }
        blog = blogDAO.save(blog);
        List<BlogDetails> blogDetails1 = new ArrayList<>();
        for (BlogDetailsVO blogDetailsVO : blogDetails
        ) {
            BlogDetails blogDetailsVO1 = new BlogDetails();
            BeanUtils.copyProperties(blogDetailsVO, blogDetailsVO1);
            blogDetailsVO1.setBlogId(blog.getId());
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
            throw new NotImplementedException("Chưa đăng nhập");

        }
        if (!(checkRole.isHavePermition(principal.getName(), "Director") ||
                checkRole.isHavePermition(principal.getName(), "Staff")) ||
                !id.isPresent()) {
            throw new NotImplementedException("User này không có quyền");
        }
        if (id.get() != blogVO.getId()) {
            throw new NotImplementedException("Chưa truyền id");
        }
        List<BlogDetails> blogDetails = blogDetailsDAO.findByBlogId(id.get());
        for (BlogDetails details : blogDetails) {
            blogDetailsDAO.deleteById(details.getId());
        }
        List<BlogDetailsVO> blogDetailsVOS = new ArrayList<>();
        for (BlogDetailsVO blogDetailsVO : blogVO.getBlogDetails()) {
            BlogDetails details = new BlogDetails();
            BeanUtils.copyProperties(blogDetailsVO, details);
            details.setBlogId(blogVO.getId());
            details = blogDetailsDAO.save(details);
            BeanUtils.copyProperties(details, blogDetailsVO);
            blogDetailsVOS.add(blogDetailsVO);
        }
        Blog blog = new Blog();
        BeanUtils.copyProperties(blogVO, blog);
        blog.setTimeCreated(Timestamp.valueOf(sdf.parse(blogVO.getTimeCreated()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
        blog = blogDAO.save(blog);
        BeanUtils.copyProperties(blog, blogVO);

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
