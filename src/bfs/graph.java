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
import java.util.concurrent.Semaphore;
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
    Semaphore sem[];
    Semaphore queueSem = new Semaphore(1);
    
    
    public graph(int v)
    {
        vertices = v;
        adjacent = new LinkedList[v];
        sem = new Semaphore[v];
        numThreads=(Runtime.getRuntime().availableProcessors());
        executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(numThreads);
        for (int i = 0 ; i < v ; i++)
        {
            adjacent[i] = new LinkedList();
            sem[i] = new Semaphore(1);
        }
    }
    
    
    
    void addEdge(int v1 , int v2)
    {
        adjacent[v1].add(v2);
    }
   
    public static graph createRandomGraph(int numOfVertices, int numOfEdges){
        graph g = new graph(numOfVertices);
        for(int i=0; i<numOfEdges;i++){
            g.addEdge((int)getRandomIntegerBetweenRange(0, numOfVertices-1), (int)getRandomIntegerBetweenRange(0, numOfVertices-1));
        }
        return g;
    }
        
        
    public static graph createRandomConnectedGraph(int numOfVertices, int numOfEdgesForOneVertice)
    {
        graph g = new graph(numOfVertices);
         for(int i=0; i<numOfVertices;i++){
                if(i!=numOfVertices-1){
                    g.addEdge(i, i+1);
                }
                for(int j=0; j<numOfEdgesForOneVertice;j++){
                       g.addEdge(i, (int)getRandomIntegerBetweenRange(0, numOfVertices-1));
                 }
         }
        return g;
    }
        
    public static double getRandomIntegerBetweenRange(double min, double max){
        double x = (int)(Math.random()*((max-min)+1))+min;
        return x;
    }
   
    public void startBFS(int source){
        boolean visited[] = new boolean[vertices] ;
        LinkedList<Integer> queue = new LinkedList();
        int level[] = new int[vertices];
        int parent[] = new int[vertices];
        int region = 1;
        for (int i =0 ; i < vertices ; i++)
        {
            level[i] = -1 ;
        }
        bfs(source, visited, queue, level, parent, region);
        int i = 0;
        for(boolean v : visited){
            if(!v){
                region++;
                bfs(i, visited, queue, level, parent, region);
            }
            i++;
        }
    }
    
    private void bfs(int source, boolean visited[],  LinkedList<Integer> queue, int level[], int parent[], int region)
    {
        level[source]  = 1;
        parent[source] = -1 ; 
        visited[source] = true;
        queue.add(source);

        while(!queue.isEmpty())
        {
            source = queue.poll();

            System.out.println("node: "+source +"  parent: " + parent[source] + "  level: " + level[source] + "  region: " + region);

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

    public void destroy()
    {
        executor.shutdown();	
    }


    public void startParallelBFS(int source) throws InterruptedException{
        boolean visited[] = new boolean[vertices] ;
        LinkedList<Integer> queue = new LinkedList();
        int level[] = new int[vertices];
        int parent[] = new int[vertices];
        int region = 1;
        
        for (int i =0 ; i < vertices ; i++)
        {
            level[i] = -1 ;
        }
        parallelBFS(source, visited, queue, level, parent, region);
        int i = 0;
        for(boolean v : visited){
            if(!v){
                region++;
                parallelBFS(i, visited, queue, level, parent, region);
            }
            i++;
        }
    }
    
    
   private void parallelBFS(int source, boolean visited[],  LinkedList<Integer> queue, int level[], int parent[], int region) throws InterruptedException
   {
        level[source]  = 1;
        parent[source] = -1 ; 
        visited[source] = true;
        queue.add(source);
        System.out.println("node: " + source + "  parent: -1  level: 1  region: " + region);  
        while(!queue.isEmpty())
        {   LinkedList<Integer> sameLevel = new LinkedList();
            while(!queue.isEmpty())
            {     
                sameLevel.add(queue.poll());
            }
            CountDownLatch controll = new CountDownLatch(sameLevel.size());
            
            LinkedList<Integer> queuesForThreads[];
            queuesForThreads = new LinkedList[sameLevel.size()];
            
            for(int i=0;i<sameLevel.size();i++)
            {
                queuesForThreads[i] = new LinkedList<>();
                int node = sameLevel.get(i);
                Iterator<Integer> neighbours = adjacent[node].listIterator();

                threadTask task = new threadTask( neighbours, node , visited , queue ,  level,  parent ,  controll, sem, queueSem, region);
                executor.execute(task);
            }
            controll.await();
            
       }    
        
        
  }
   
}