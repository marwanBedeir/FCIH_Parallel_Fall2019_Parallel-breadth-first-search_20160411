/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bfs;


public class BFS {

    public static void main(String[] args) throws InterruptedException {
        
       graph g = graph.createRandomConnectedGraph(50, 5);
//        graph g = new graph(31);
//            g.addEdge(0,1);
//            g.addEdge(0,2);
//            g.addEdge(0,3);
//            g.addEdge(0,4);
//            g.addEdge(0,5);
//            g.addEdge(0,6);
//            g.addEdge(0,7);
//            g.addEdge(0,8);
//            g.addEdge(0,9);
//            g.addEdge(0,10);
//            g.addEdge(1,11);
//            g.addEdge(2,11);
//            g.addEdge(3,11);
//            g.addEdge(4,11);
//            g.addEdge(5,11);
//            g.addEdge(6,11);
//            g.addEdge(7,11);
//            g.addEdge(8,11);
//            g.addEdge(9,11);
//            g.addEdge(10,11);
            
            
//        g.addEdge(0,1);
//        g.addEdge(0,2);
//        g.addEdge(0,3);
//        g.addEdge(2,6);
//        g.addEdge(2,4);
//        g.addEdge(2,5);
//        g.addEdge(1,8);
//        g.addEdge(1,7);
//        g.addEdge(1,9);
//        g.addEdge(5,17);
//        g.addEdge(5,18);
//        g.addEdge(4,10);
//        g.addEdge(4,11);
//        g.addEdge(8,11);
//        g.addEdge(8,12);
//        g.addEdge(7,13);
//        g.addEdge(7,14);
//        g.addEdge(9,15);
//        g.addEdge(9,16);
//        g.addEdge(18,10);
//        g.addEdge(18,11);
//        g.addEdge(18,19);
//        g.addEdge(19,20);
//        g.addEdge(13,20);
//        g.addEdge(2,28);
//        g.addEdge(0,30);
//        g.addEdge(15,23);
//        g.addEdge(12,25);
//        g.addEdge(16,24);
//        g.addEdge(14,21);
//        g.addEdge(14,22);
//        g.addEdge(22,29);
//        g.addEdge(29,21);
//        g.addEdge(17,26);
//        g.addEdge(18,26);
//        g.addEdge(18,27);
//        g.addEdge(30,27);
        
        
        
        
        long startTime = System.currentTimeMillis();
        g.startBFS(0);
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println("Execution Time:" + " " + elapsedTime + "  milli sec");
        
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        System.out.println("***************************************************");
        
        startTime = System.currentTimeMillis();
        g.startParallelBFS(0);
        g.destroy();
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;        
        System.out.println("Execution Time:" + " " + elapsedTime + "  milli sec");
        

    }
    
    
    

    
}
