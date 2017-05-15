package core;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author kam
 */
public class Dijkstra {
    private double dist[];
    private int n;
    private int m;
    private ArrayList<Integer> adj[];
    private ArrayList<Double> weight[];
    private boolean used[];
    private int pred[];
    
    public Dijkstra(ArrayList firestations, ArrayList lines) {
        n = firestations.size() + 1;
        int m = lines.size();
        adj = getMatV(Memory.lines, n, m);
        weight = getMatR(Memory.lines, n, m);  
        used = new boolean[n];
        Arrays.fill(used, false);
        pred = new int[n];
        Arrays.fill(pred, -1);
        dist = new double[n];
        Arrays.fill(dist, Const.INF);   
        dejkstra(0);
    }
    
    private void dejkstra(int s) { 
        dist[s] = 0;
        for (int iter = 0; iter < n; iter++) {
            int v = -1;
            double distV = Const.INF;
            for (int i = 0; i < n; i++) {
                if (used[i]) {
                    continue;
                }
                if (distV < dist[i]) {
                    continue;
                }
                v = i;
                distV = dist[i];
            }
            for (int i = 0; i < adj[v].size(); i++) {
                int u = adj[v].get(i);
                double weightU = weight[v].get(i);
                if (dist[v] + weightU < dist[u]) {
                    dist[u] = dist[v] + weightU;
                    pred[u] = v;
                }
            }
            used[v] = true;
        }
    }
    
    private ArrayList[] getMatV(ArrayList mas, int n, int m) {
        ArrayList[] adj = new ArrayList[n];
        
        for (int i = 0; i < n; ++i) {
            adj[i] = new ArrayList<Integer>();
        }
        
        for (int i = 0; i < m; ++i) {
            Rebro buf = (Rebro) mas.get(i);
            int u = buf.v1;
            int v = buf.v2;
            adj[u].add(v);
        }
        
        return adj;
    }
    
    private ArrayList[] getMatR(ArrayList mas, int n, int m) {
        ArrayList[] weight = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            weight[i] = new ArrayList<Double>();
        }

        for (int i = 0; i < m; i++) {
            Rebro buf = (Rebro) mas.get(i);
            weight[buf.v1].add(buf.length);
        }

        return weight;
    }
    
    public double[] getPath() {
        return Changer.remove(dist, 0);
    }   
    
    
}
