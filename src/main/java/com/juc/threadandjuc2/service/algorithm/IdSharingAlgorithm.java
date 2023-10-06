package com.juc.threadandjuc2.service.algorithm;

import org.apache.shardingsphere.sharding.api.sharding.ShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.*;

public class IdSharingAlgorithm implements HintShardingAlgorithm<Integer> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames,
                                         HintShardingValue<Integer> shardingValue) {
        System.out.println("shardingValue=" + shardingValue); //表示 库
        System.out.println("availableTargetNames=" + availableTargetNames); //表示 表
        Collection<String> shardingList = new LinkedList<>();
        for (String each : availableTargetNames){
            for (Integer val : shardingValue.getValues()){
                if(each.endsWith(val + "")){
                    shardingList.add(each);
                }
            }
        }
        return shardingList;
    }
//    @Override
//    public Collection<String> doSharding(Collection<String> availableTargetNames,
//                                         ShardingValue shardingValue) {
//        System.out.println("shardingValue=" + shardingValue);
//        System.out.println("availableTargetNames=" + availableTargetNames);
//        List<String> shardingResult = new ArrayList<>();
//        for (String targetName : availableTargetNames) {
//            String suffix = targetName.substring(targetName.length() - 1);
//            if (StringUtils.isNumber(suffix)) {
//                ListShardingValue<Integer> tmpSharding = (ListShardingValue<Integer>) shardingValue;
//                for (Integer value : tmpSharding.getValues()) {
//                    if (value % 2 == Integer.parseInt(suffix)) {
//                        shardingResult.add(targetName);
//                    }
//                }
//            }
//        }
//        return shardingResult;
//    }


    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return null;
    }
}
