package com.sjtusummerproject.commentmicroservice.Controller;

import com.sjtusummerproject.commentmicroservice.DataModel.Dao.CommentRepository;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.CommentEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.ReplyEntity;
import com.sjtusummerproject.commentmicroservice.DataModel.Domain.UserEntity;
import com.sjtusummerproject.commentmicroservice.Service.CommentService;
import com.sjtusummerproject.commentmicroservice.Service.ReplyService;
import com.sjtusummerproject.commentmicroservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(value = "/Comment")
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;
    @Autowired
    ReplyService replyService;
    @Autowired
    RestTemplate restTemplate;

    @Value("${comment.page.size}")
    private int PageSize;
    @Value("${comment.page.offset}")
    private int PageOffset;
    @Value("${authservice.url}")
    private String authUrl;

    /*按照时间顺序 递减排列*/
    public Pageable createPageable(HttpServletRequest request){
        return new PageRequest(Integer.parseInt(request.getParameter("pagenumber"))-PageOffset, PageSize, new Sort(Sort.Direction.DESC, "createTime"));
    }

    @RequestMapping(value = "/Add")
    @ResponseBody
    public String add(@RequestParam(value = "token") String token, @RequestParam(value = "ticketid") Long targetTicketId,
                      @RequestParam(value = "content",defaultValue = "")String content, HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        if(content == null||content.trim().equals(""))
            return "the content is null";
        commentService.save(userEntity.getId(),targetTicketId,content);
        return "ok";
    }

    @RequestMapping(value = "/QueryByUserid")
    @ResponseBody
    public Page<CommentEntity> queryByUserid(@RequestParam(value = "token") String token,HttpServletRequest request,HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        return commentService.queryByOwnerId(userEntity.getId(),createPageable(request));
    }

    @RequestMapping(value = "/QueryByTicketid")
    @ResponseBody
    public Page<CommentEntity> queryByTicketid(@RequestParam(value = "ticketid") Long ticketid,HttpServletRequest request,HttpServletResponse response){

        return commentService.queryByTicketId(ticketid,createPageable(request));
    }

    @RequestMapping(value = "/UpdateContentByCommentid")
    @ResponseBody
    public CommentEntity updateContentByCommentid(@RequestParam(value = "token") String token,@RequestParam(value = "commentid") Long commentId,@RequestParam(value = "content") String content,HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        if(content.trim().equals(""))
            return null;
        return commentService.updateContentByCommentid(commentId,content);
    }

    @RequestMapping(value = "/DeleteByCommentid")
    @ResponseBody
    public CommentEntity deleteByCommentid(@RequestParam(value = "token") String token,@RequestParam(value = "commentid") Long commentId,@RequestParam(value = "content") String content,HttpServletResponse response){
        UserEntity userEntity = callAuthService(token);
        int result = authUser(userEntity);
        response.addIntHeader("errorNum", result);
        if (result != 0) return null;

        replyService.deleteByParentId(commentId);
        return commentService.deleteByCommentid(commentId);
    }
    /*****************************************************************/
    /** 测试 **/
    @RequestMapping(value = "/test1")
    @ResponseBody
    public String test1(){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent("123123123123");

        commentRepository.save(commentEntity);
        // service.update(stu);
        System.out.println("已生成ID：" + commentEntity.getId());

        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent("1234");

        commentRepository.save(commentEntity1);
        // service.update(stu);
        System.out.println("已生成ID1：" + commentEntity1.getId());
        return "ok";
    }

    private UserEntity callAuthService(String token){
        System.out.println("the token is : " +token);
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);
        return restTemplate.postForObject(authUrl, multiValueMap, UserEntity.class);
    }

    private int authUser(UserEntity userEntity){

        if (userEntity == null) return 1;
        else if (!userEntity.getAuthority().equals("Customer")) return 2;
        else if (userEntity.getStatus().equals("Frozen")) return 3;
        else return 0;
    }
}
