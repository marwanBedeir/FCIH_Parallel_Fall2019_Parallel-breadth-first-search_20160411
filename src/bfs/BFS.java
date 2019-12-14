/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bfs;


public class BFS {

    public static void main(String[] args) throws InterruptedException {
        
        graph g = new graph(8);
        g.addEdge(0,2);
        g.addEdge(0,6);
        g.addEdge(0,4);
        g.addEdge(1,0);
        g.addEdge(1,4);
        g.addEdge(2,7);
        g.addEdge(3,5);
        g.addEdge(4,3);
        g.addEdge(5,1);
        g.addEdge(6,2);
        g.addEdge(6,5);
        g.addEdge(6,7);
        g.addEdge(7,0);
        g.addEdge(7,1);
        
        
        
        
        long startTime = System.currentTimeMillis();
        g.bfs(6);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution Time:" + " " + elapsedTime + "  milli sec");
        
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        
        startTime = System.currentTimeMillis();
        g.parallelBFS(6);
        g.destroy();
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;        
        System.out.println("Execution Time:" + " " + elapsedTime + "  milli sec");
        

    }
    
    
    

    
}
