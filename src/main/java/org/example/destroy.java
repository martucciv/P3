package org.example;
/*Veronica Martucci
* COP3503 Spring 2022
* P3: Destroying Connectivity
*/
import java.util.Arrays;
import java.util.Scanner;

public class destroy {

    public static void main(String[] args){

        int numComputers, numConnections, numDestroy;

        Scanner stdin = new Scanner(System.in);

        //create the individual sets
        numComputers = stdin.nextInt();

        //create disjoint object
        djSet djSet = new djSet(numComputers);

        numConnections = stdin.nextInt();
        numDestroy = stdin.nextInt();

        int[][] connections = new int[numConnections + 1][2];
        //store the connections in an array. Index 0 will be empty and start filling array at index 1
        for(int i = 1; i <= numConnections; i++){

            connections[i][0] = stdin.nextInt();
            connections[i][1] = stdin.nextInt();
        }

        //create boolean array
        boolean[] bool = new boolean[numConnections + 1];

        //set all indexes in boolean arr to true
        Arrays.fill(bool, true);

        int[] destroy = new int[numDestroy + 1];
        //store the pairs to destroy in destroy[] and change that index of bool[] to false
        for(int i = 0; i < numDestroy; i++){

            destroy[i] = stdin.nextInt();
            bool[destroy[i]] = false;
        }

        //union all of the connections after all the destruction
        for(int i = 1; i <= numConnections; i++){

            //if bool[i] == true, union
            if(bool[i]){

                int v1 = connections[i][0];
                int v2 = connections[i][1];
                djSet.union(v1, v2);
            }
        }

        long[] connectivity = new long[numDestroy + 2];
        //Store the last connectivity after all destruction in the last index of the array
        connectivity[numDestroy + 1] = calculateConnectivity(numComputers);

        //start at end of destroy[] array and union each destroy connection one at a time, calculating
        //the connectivity after each union
        for(int i = numDestroy; i>= 0; i--){

            int idx = destroy[i];
            bool[idx] = true;

            int v1 = connections[idx][0];
            int v2 = connections[idx][1];
            djSet.union(v1, v2);
            connectivity[i] = calculateConnectivity(numComputers);
        }

        //print each connectivity
        print(numDestroy, connectivity);
    }

    //calculate the total connectivity and return the result
    public static int calculateConnectivity(int numComputers) {

        int[] freq= new int[numComputers];

        //loop the number of computers and mark each time a root is used to know how many times a root is used
        for(int i = 0; i < numComputers; i++) {

            freq[djSet.find(i)]++;
        }

        int res = 0;

        //loop through the array and calculate the result
        for(int i = 0; i < numComputers; i++){
            res += Math.pow(freq[i], 2);
        }

        return res;
    }

    //print the connectivity
    public static void print(int numDestroy, long[] connectivity){

        for(int i = 0; i < numDestroy + 1; i++){
            System.out.println(connectivity[i]);
        }
    }
}

class djSet{

    public static int[] parent;
    final public int MAX = 100000;

    public djSet(int n){

        parent = new int[MAX];
        for(int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    //Find the root of the tree
    public static int find(int v) {

        //at root of tree
        if(parent[v] == v){
            return v;
        }

        //find parent's root
        int root = find(parent[v]);

        //attach directly to root
        parent[v] = root;
        return root;
    }

    //Connect the two roots
    public void union(int v1, int v2) {

        //find the roots
        int root1 = find(v1);
        int root2 = find(v2);

        //attach v2 to v1
        if(root1 != root2){

            parent[root2] = root1;
        }
    }
}