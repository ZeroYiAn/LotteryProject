package cn.itedus.lottery.interfaces.assembler;

import cn.itedus.lottery.domain.strategy.model.vo.DrawAwardVO;
import cn.itedus.lottery.rpc.activity.booth.dto.AwardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
/**
 *
 *
 *@description:
 *@author: ZeroYiAn
 *@time: 2023/6/4
 *
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AwardMapping extends IMapping<DrawAwardVO, AwardDTO> {

    /**
     * 将 DrawAwardVO 对象转换为  AwardDTO对象
     * @param var1 源对象: VO 表现层对象
     * @return 目标对象：DTO 数据传输对象
     *
     * 如果一些接口字段在两个对象间不是同名的，则需要进行配置，就像 uId -> userId
     */
    @Mapping(target = "userId", source = "uId")
    @Override
    AwardDTO sourceToTarget(DrawAwardVO var1);

    /**
     * 将 AwardDTO 转换为 DrawAwardVO
     * @param var1 目标对象：DTO 数据传输对象
     * @return  源对象: VO 表现层对象
     */
    @Override
    DrawAwardVO targetToSource(AwardDTO var1);

}