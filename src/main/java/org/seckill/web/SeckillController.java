package org.seckill.web;

import enums.SeckillStateEnum;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillResult;
import org.seckill.dto.seckillExecuetion;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.seckillCloseException;
import org.seckill.exception.seckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill") //url:/模块/字典/资源/{id}/细分 /seckill/list

public class SeckillController {
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model){
        //获取列表页
        //list.jsp+model=ModelAndView
        logger.info("*******开始获取列表详情**************");
        List<Seckill> list = seckillService.getSeckillList();
        model.addAttribute("list",list);
        System.out.println(list);
        logger.info("*******结束获取列表详情**************");
        return "list"; //在spring-web中配置过，实际上代表WEB-INF/jsp/"list".jsp

    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model){
        if(seckillId==null){
            return "redirect:/seckill/list";
        }
        Seckill seckill=seckillService.getById(seckillId);
        if(seckill==null){
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill",seckill);
        return "detail";
    }

    @RequestMapping(value = "/{seckillID}/exposer",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposer(Long seckillId){
        SeckillResult<Exposer> result;
        try {
            Exposer exposer=seckillService.exportSeckillUrl(seckillId);
            result=new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            result=new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public SeckillResult<seckillExecuetion> execute(Long seckillId, String md5, @CookieValue(value = "killPhone",required = false) Long phone){
        if(phone==null){
            return new SeckillResult<seckillExecuetion>(false,"未注册");
        }
        SeckillResult<seckillExecuetion> result;
        try {
            seckillExecuetion execuetion=seckillService.executeSeckill(seckillId,phone,md5);
            return new SeckillResult<seckillExecuetion>(true,execuetion);
        } catch(RepeatKillException e){
            seckillExecuetion execuetion=new seckillExecuetion(seckillId, -1,SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<seckillExecuetion>(false,execuetion);

        }catch (seckillCloseException e){
            seckillExecuetion execuetion=new seckillExecuetion(seckillId,0, SeckillStateEnum.END);
            return new SeckillResult<seckillExecuetion>(false,execuetion);

        }
        catch (seckillException e) {
            logger.error(e.getMessage(),e);
            seckillExecuetion execuetion=new seckillExecuetion(seckillId, -2,SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<seckillExecuetion>(false,execuetion);
        }

    }

    @RequestMapping(value="/time/now",method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time(){
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
