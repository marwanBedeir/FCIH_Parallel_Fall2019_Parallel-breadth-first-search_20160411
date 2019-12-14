/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bfs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 *
 * @author LEGION
 */
public class graph {
    
    int vertices;
    LinkedList<Integer> adjacent[];
    ThreadPoolExecutor executor;
    int numThreads;
    
    
    
    public graph(int v)
    {
        vertices = v;
        adjacent = new LinkedList[v];
        numThreads=(Runtime.getRuntime().availableProcessors());
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        for (int i = 0 ; i < v ; i++)
        {
            adjacent[i] = new LinkedList();
        }
    }
    
    
    
        void addEdge(int v1 , int v2)
        {
            adjacent[v1].add(v2);
            
        }
   
 
        
void bfs(int source)
{
    boolean visited[] = new boolean[vertices] ;
    LinkedList<Integer> queue = new LinkedList();
    int level[] = new int[vertices];
    int parent[] = new int[vertices];
    
    for (int i =0 ; i < vertices ; i++)
    {
        level[i] = -1 ;
    }
    int count = 0;
    level[source]  = count+1;
    parent[source] = -1 ; 
    visited[source] = true;
    queue.add(source);
    
    while(queue.size() != 0)
    {
        source = queue.poll();
        
        System.out.println("node: "+source +"  parent:" + parent[source] + "  level:" + level[source]);
        
        Iterator<Integer> neighbours = adjacent[source].listIterator();
        while(neighbours.hasNext())
        {
            int n = neighbours.next();
            
            if (!visited[n])
            {   
                parent[n] = source;
                level[n] = level[source] + 1 ;
                visited[n] = true;
                queue.add(n);
            }
            
        }
        
    }
}

public	void	destroy()
{
    executor.shutdown();	
}


   void parallelBFS(int source) throws InterruptedException
   {
       
       
    boolean visited[] = new boolean[vertices] ;
    LinkedList<Integer> queue = new LinkedList();
    int level[] = new int[vertices];
    int parent[] = new int[vertices];
    
    for (int i =0 ; i < vertices ; i++)
    {
        level[i] = -1 ;
    }
    int count = 0;
    level[source]  = count + 1;
    parent[source] = -1 ; 
    visited[source] = true;
    queue.add(source);
    
    while(queue.size() != 0)
    {   
 
        int d = count + 1;
        LinkedList<Integer> sameLevel = new LinkedList();
    
        while(queue.size() != 0)
        {     
             int peek = queue.peekFirst();
             
             if(d == 1)
             {
             
                 System.out.println("node: "+source +"  parent:" + parent[source] + "  level:" + level[source]);
                 sameLevel.add(peek);
                 queue.poll();
             }
             else
             {
                     if(d == level[peek])
                    {
                        sameLevel.add(peek);
                        queue.poll();
                    }
                    else
                    {
                        break;
                    }
             }
             
        }
        
        CountDownLatch controll = new CountDownLatch(sameLevel.size());
        
        for(int i :sameLevel)
        {
            Iterator<Integer> neighbours = adjacent[i].listIterator();
            
            threadTask task = new threadTask( neighbours, i , visited , queue ,  level,  parent ,  controll);
            executor.execute(task);
        }
        controll.await();
        count++;
    }
 
       
   }
        
}