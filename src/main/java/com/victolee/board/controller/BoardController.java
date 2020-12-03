package com.victolee.board.controller;

import com.victolee.board.domain.entity.UserEntity;
import com.victolee.board.dto.BoardDto;
import com.victolee.board.dto.BoardIdAddressDto;
import com.victolee.board.service.BoardService;
import com.victolee.board.service.CartService;
import lombok.AllArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private CartService cartService;


    //-----------------------------------jpa로만든 컨트롤러-------------------------------------------------
    /* 메인 화면 */
    @GetMapping("/")
    public String list(@AuthenticationPrincipal UserEntity userEntity,Model model) {
        String role = userEntity.getRole();
        List<BoardDto> boardList = boardService.getBoardlistcount();
        model.addAttribute("boardList", boardList);
        model.addAttribute("role",role);
        return "/index";
    }


    /* 게시글 전체 목록 */ /* 페이지 수를 담는 배열과  그 페이지에 따라 게시글 목록들을 담은 리스트 */
    @GetMapping("/managerlist")
    public String list(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "/managerlist";
    }



    //게시물 아이디와 게시물 주소값 , 좌표값 , 그리고 게시물 제목과 작성자 가져오기
    @RequestMapping(value = "/board/address", method = RequestMethod.GET)
    public @ResponseBody Map<Long,BoardIdAddressDto> getboardIdAddressList(Model model){
        List<BoardIdAddressDto> boardDtoList = boardService.getBoardIdAddress(); // DB에 등록된 User List를 받아온다.


        Map<Long,BoardIdAddressDto> result = new HashMap<Long,BoardIdAddressDto>();
        for (BoardIdAddressDto i : boardDtoList) result.put(i.getId(),i);

        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<Long, BoardIdAddressDto> entry : result.entrySet() ) {
            Long key = entry.getKey();
            BoardIdAddressDto value = entry.getValue();
            jsonObject.put(key, value);
        }

        return jsonObject;
    }


    @GetMapping("/countmanagerlist")
    public String likelist(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlistcountsort(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "/managerlist";
    }
    @GetMapping("/Datemanagerlist")
    public String Datelist(Model model, @RequestParam(value="page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlistdate(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("boardList", boardList);
        model.addAttribute("pageList", pageList);

        return "/managerlist";
    }



    /* 게시글 상세 목록*/
    @RequestMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model
            ,Principal principal) {

        BoardDto boardDto = boardService.getPost(no);


        if(!boardDto.getWriter().equals(principal.getName())) {
            int count = boardDto.getBcount();
            count = count + 1;
            boardDto.setBcount(count);
            boardService.savePost(boardDto);
        }

        model.addAttribute("userId",principal.getName());
        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }


    /* 게시글 쓰기 폼으로 이동*/
    @GetMapping("/post")
    public String write() {

        return "board/write";
    }


    /* 게시글 쓰기 */ /* 로그인한 유저가 작성자가 되도록 해줌.*/
    /* 게시물의 썸네일 파일업로드 포함*/
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String write(@RequestParam("img") MultipartFile files, BoardDto boardDto, Principal principal,Map<String, Object> map) {

        try {



            String baseDir = "C:\\JAVA_Spring\\캡스톤 프로젝트\\spring_practice\\src\\main\\resources\\static\\images\\media";//파일 저장 코드


            String filePath = baseDir + "\\" + files.getOriginalFilename();
            String fileName = files.getOriginalFilename();
            files.transferTo(new File(filePath));//해당 위치에 저장 형준 수정

            String userid = principal.getName();
            boardDto.setImgname(fileName);
            boardDto.setWriter(userid);
            boardService.savePost(boardDto);

            return "redirect:/managerlist";
        } catch(Exception e) {
            e.printStackTrace();
        }

        return "redirect:/managerlist";
    }

    /* 게시글 수정 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BoardDto boardDto = boardService.getPost(no);

        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }
    /* 수정 폼에서 수정 완료*/
    @PutMapping("/post/edit/{no}")
    public String update(@RequestParam("img") MultipartFile files, BoardDto boardDto, Principal principal,Map<String, Object> map) {
        try {


            String baseDir = "C:\\JAVA_Spring\\캡스톤 프로젝트\\spring_practice\\src\\main\\resources\\static\\images\\media";//파일 저장 코드



            String filePath = baseDir + "\\" + files.getOriginalFilename();
            String fileName = files.getOriginalFilename();
            files.transferTo(new File(filePath));//해당 위치에 저장 형준 수정

            String userid = principal.getName();
            boardDto.setImgname(fileName);
            boardDto.setWriter(userid);
            boardService.savePost(boardDto);

            return "redirect:/managerlist";
        } catch(Exception e) {
            e.printStackTrace();
        }


        return "redirect:/managerlist";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        boardService.deletePost(no);

        return "redirect:/managerlist";
    }

    /* 게시글 검색 */ //키워드를 받아서 검색.
    @GetMapping("/board/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) {
        List<BoardDto> boardDtoList = boardService.searchPosts(keyword);

        model.addAttribute("boardList", boardDtoList);

        return "/managerlist";
    }


    /* 주소를 검색하여 찾는곳  */
    @GetMapping("/map")
    public String map(){

        return "/map";
    }
    /* 주소찾기 테스트 페이지*/
    @GetMapping("/maptest")
    public String maptest(){

        return "/test2";
    }



    //섬머노트 기능 ,다중 파일 업로드를 위해.
    @RequestMapping(value="/mine/imageUpload.do", method = RequestMethod.POST)
    public void imageUpload(HttpServletRequest request,
                            HttpServletResponse response, MultipartHttpServletRequest multiFile
            , @RequestParam MultipartFile upload) throws Exception{
        // 랜덤 문자 생성
        UUID uid = UUID.randomUUID();

        OutputStream out = null;
        PrintWriter printWriter = null;

        //인코딩
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        try{

            //파일 이름 가져오기
            String fileName = upload.getOriginalFilename();
            byte[] bytes = upload.getBytes();

            //이미지 경로 생성



            String path = "C:\\JAVA_Spring\\캡스톤 프로젝트\\spring_practice\\src\\main\\resources\\static\\images\\summernote";// fileDir는 전역 변수라 그냥 이미지 경로 설정해주면 된다.


            String ckUploadPath = path + uid + "_" + fileName;
            File folder = new File(path);

            //해당 디렉토리 확인
            if(!folder.exists()){
                try{
                    folder.mkdirs(); // 폴더 생성
                }catch(Exception e){
                    e.getStackTrace();
                }
            }

            out = new FileOutputStream(new File(ckUploadPath));
            out.write(bytes);
            out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화

            String callback = request.getParameter("CKEditorFuncNum");
            printWriter = response.getWriter();
            String fileUrl = "/mine/ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName;  // 작성화면

            // 업로드시 메시지 출력
            printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
            printWriter.flush();

        }catch(IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(out != null) { out.close(); }
                if(printWriter != null) { printWriter.close(); }
            } catch(IOException e) { e.printStackTrace(); }
        }

        return;
    }

    /**
     * cKeditor 서버로 전송된 이미지 뿌려주기
     * @param uid
     * @param fileName
     * @param request
     * @return
     * @throws ServletException
     * @throws IOException
     */
    //
    @RequestMapping(value="/mine/ckImgSubmit.do")
    public void ckSubmit(@RequestParam(value="uid") String uid
            , @RequestParam(value="fileName") String fileName
            , HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        //서버에 저장된 이미지 경로



        String path = "C:\\JAVA_Spring\\캡스톤 프로젝트\\spring_practice\\src\\main\\resources\\static\\images\\summernote";



        String sDirPath = path + uid + "_" + fileName;

        File imgFile = new File(sDirPath);

        //사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다.
        if(imgFile.isFile()){
            byte[] buf = new byte[1024];
            int readByte = 0;
            int length = 0;
            byte[] imgBuf = null;

            FileInputStream fileInputStream = null;
            ByteArrayOutputStream outputStream = null;
            ServletOutputStream out = null;

            try{
                fileInputStream = new FileInputStream(imgFile);
                outputStream = new ByteArrayOutputStream();
                out = response.getOutputStream();

                while((readByte = fileInputStream.read(buf)) != -1){
                    outputStream.write(buf, 0, readByte);
                }

                imgBuf = outputStream.toByteArray();
                length = imgBuf.length;
                out.write(imgBuf, 0, length);
                out.flush();

            }catch(IOException e){

            }finally {

            }
        }
    }

}
