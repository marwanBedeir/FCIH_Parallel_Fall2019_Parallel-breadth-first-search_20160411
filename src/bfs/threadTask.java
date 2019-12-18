/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bfs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MMB
 */
public class threadTask implements Runnable
{   
    int source;
    boolean visited[] ;
    LinkedList<Integer> queue ;
    Iterator neighbours;
    int level[] ;
    int parent[] ;
    CountDownLatch controll;
    Semaphore sem[];
    Semaphore queueSem;
    int region;
            
    public threadTask(Iterator neighbours,int source, boolean visited[] , LinkedList<Integer> queue , int level[] , int parent[] , CountDownLatch controll, Semaphore sem[], Semaphore queueSem, int region)
    {   
        this.neighbours = neighbours;
        this.source = source;
        this.level  = level;
        this.parent = parent;
        this.queue = queue;
        this.visited = visited;
        this.controll = controll;
        this.sem = sem;
        this.region = region;
        this.queueSem = queueSem;
    }

    @Override
    public void run()
    {   while(neighbours.hasNext())
        { 
            try 
            {
                int node = (int )neighbours.next();
                sem[node].acquire();
                if (!visited[node])
                {   
                    //System.out.println(source);
                    visited[node] = true;
                    parent[node] = source;
                    level[node] = level[source] + 1 ;  
                    queueSem.acquire();
                    queue.add(node);
                    queueSem.release();
                    System.out.println("node: "+node +"  parent: " + parent[node] + "  level: " + level[node] + "  region: " + region);  
                }
                sem[node].release();
            } catch (InterruptedException ex) {
                Logger.getLogger(threadTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            
          controll.countDown();
        
    }
    
}
