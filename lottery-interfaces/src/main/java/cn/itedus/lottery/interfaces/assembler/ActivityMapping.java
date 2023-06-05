package cn.itedus.lottery.interfaces.assembler;

import cn.itedus.lottery.domain.activity.model.vo.ActivityVO;
import cn.itedus.lottery.rpc.activity.deploy.dto.ActivityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @description: 活动对象转换(映射)配置，
 * @author: ZeroYiAn
 * @time: 2023/6/4
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActivityMapping extends IMapping<ActivityVO, ActivityDTO>{

    /**
     * 将 List<ActivityVO> 转换为 List<ActivityDTO>
     * @param var1 源对象: VO 表现层对象
     * @return  目标对象：DTO 数据传输对象
     */
    @Override
    List<ActivityDTO> sourceToTarget(List<ActivityVO> var1);

}
