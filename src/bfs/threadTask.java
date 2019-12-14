/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bfs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author LEGION
 */
public class threadTask implements Runnable
{   
    int source;
    boolean visited[] ;
    LinkedList<Integer> queue   ;
    Iterator neighbours;
    int level[] ;
    int parent[] ;
    CountDownLatch controll;
    
    public threadTask(Iterator neighbours,int source, boolean visited[] , LinkedList<Integer> queue , int level[] , int parent[] , CountDownLatch controll)
    {   
        this.neighbours = neighbours;
        this.source = source;
        this.level  = level;
        this.parent = parent;
        this.queue = queue;
        this.visited = visited;
        this.controll = controll;
        
    }

    @Override
    public void run()
    {   while(neighbours.hasNext())
        {
            int node = (int )neighbours.next();
            if (!visited[node])
            {   
                visited[node] = true;
                parent[node] = source;
                level[node] = level[source] + 1 ;               
                queue.add(node);
            System.out.println("node: "+node +"  parent:" + parent[node] + "  level:" + level[node]);  

            }
        }
            
          controll.countDown();
        
    }
    
}
