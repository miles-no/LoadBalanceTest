package com.loadbalance;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by KshitijBahul on 29-08-2017.
 */
@RestController
public class LoadTest {

    private Map<Integer,EachQueue> queueMap;
    Integer totalLoadOnQueues = 0 ;
    @PostMapping("/loadTest")
    public void testLoadBalancer(@RequestBody List<Integer> loadFactors){

            queueMap = new HashMap<>();
            totalLoadOnQueues = 0 ;
        IntStream.range(0,loadFactors.size()).forEach(index -> {
            queueMap.put(index,new EachQueue(loadFactors.get(index)));
        });
        System.out.println("The queue Map is "+queueMap.size());
        /*
        * After we have the desired queues we run 3 iterations
        1. With 20 threads and check distribution in the queue
        2. With 50 threads and check distribution in the queue
        3. With 100 threads and check distribution in the queue

        */
        for (int i=0;i<10;i++){
            int index= getQueueToBePopulated();
            if (index>=0){
                queueMap.get(index).getCalls().add(true);
                totalLoadOnQueues++;
            }
        }
        // Round 1 done
        System.out.println("Round ****** 1");
        queueMap.forEach((k,v)->{System.out.println("Key "+ k+"  Size "+v.getCalls().size());});
        System.out.println("Round ****** 1 Done ");
        clearCalls();
        for (int i=0;i<50;i++){
            //System.out.println("In 2 loop is "+i);
            int index= getQueueToBePopulated();
            if (index>=0){
                queueMap.get(index).getCalls().add(true);
                totalLoadOnQueues++;
            }
        }
        // Round 2 done

        System.out.println("Round ****** 2");
        queueMap.forEach((k,v)->{System.out.println("Key "+ k+" Size "+v.getCalls().size());});
        System.out.println("Round ****** 2 done");
        clearCalls();
        for (int i=0;i<100;i++){
            int index= getQueueToBePopulated();
            if (index>=0){
                queueMap.get(index).getCalls().add(true);
                totalLoadOnQueues++;
            }
        }
        // Round 3 done
        System.out.println("Round ****** 3");
        queueMap.forEach((k,v)->{System.out.println("Key "+ k+" Size "+v.getCalls().size());});
        System.out.println("Round ****** 3 done");

    }
    private Integer getQueueToBePopulated(){
        //calculate total calls on the queue
        for(int i=0 ; i < queueMap.size();i++){
            if (queueMap.get(i).getCalls().size() == 0 && queueMap.get(i).getLoadFactor() >0) {
                return i;
            }else if(((double)queueMap.get(i).getCalls().size()/totalLoadOnQueues)*100 <= queueMap.get(i).getLoadFactor()){
                return i;
            }
        }
        System.out.println("No Index to return . I don't know why. The logic looked unbreakable ;) ");
        return -1;
    }
    private void clearCalls (){
        queueMap.forEach((k,v)->{v.clearCalls();});
        totalLoadOnQueues = 0;
    }
    //public ResponseEntity testWithLoad()
}
