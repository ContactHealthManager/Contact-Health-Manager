package com.victolee.board.controller;

import com.victolee.board.dto.ServiceCenterDto;
import com.victolee.board.service.ServiceCenterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor


public class ServiceCenterController {
    private ServiceCenterService servicecenterService;


    /* 게시글 목록 */
    @GetMapping("/servicecenter")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<ServiceCenterDto> servicecenterList = servicecenterService.getServiceCenterlist(pageNum);
        Integer[] pageList = servicecenterService.getPageList(pageNum);

        model.addAttribute("servicecenterList", servicecenterList);
        model.addAttribute("pageList", pageList);

        return "/servicecenter";
    }



    /* 게시글 상세 */
    @GetMapping("/servicepost/{no}")
    public String detail(@PathVariable("no") Long no, Model model,Principal principal) {

        ServiceCenterDto servicecenterDTO = servicecenterService.getPost(no);


        model.addAttribute("userId",principal.getName());
        model.addAttribute("servicecenterDTO", servicecenterDTO);



        return "servicecenter/service_detail";
    }


    /* 게시글 쓰기 */
    @GetMapping("/servicepost")
    public String write() {
        return "servicecenter/service_write";
    }

    /* 게시글 쓰기 */
    @RequestMapping(value = "/servicepost", method = RequestMethod.POST)

    public String write(ServiceCenterDto servicecenterDTO, Principal principal) {

        String userid = principal.getName();

        servicecenterDTO.setUser(userid);
        servicecenterService.savePost(servicecenterDTO);

        return "redirect:/servicecenter";
    }



    /* 게시글 수정 */
    @GetMapping("/servicepost/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        ServiceCenterDto servicecenterDTO = servicecenterService.getPost(no);

        model.addAttribute("servicecenterDTO", servicecenterDTO);
        return "ServiceCenter/service_update";
    }
    /* 수정 폼에서 수정 완료*/
    @PutMapping("/servicepost/edit/{no}")
    public String update(ServiceCenterDto servicecenterDTO,Principal principal) {
        String userid = principal.getName();

        servicecenterDTO.setUser(userid);
        servicecenterService.savePost(servicecenterDTO);

        return "redirect:/servicecenter";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/servicepost/{no}")
    public String delete(@PathVariable("no") Long no) {
        servicecenterService.deletePost(no);

        return "redirect:/servicecenter";
    }



}
