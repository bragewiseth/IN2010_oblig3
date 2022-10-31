import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Stack;



class Innleveringsoppgave3
{


        //  main

        public static void main(String[] args) throws IOException 
        {
            long startTime = System.nanoTime();
    
            Innleveringsoppgave3 G = byggGraf(args);
    
            System.out.println("<<<<<<OPPGAVE 1>>>>>>\n");
    
            G.printGraf();  
            System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
    
            System.out.println("<<<<<<OPPGAVE 2>>>>>>\n");
    
            G.shortestPathBFS(G.V.get("nm2255973"), G.V.get("nm0000460")); 
            G.shortestPathBFS(G.V.get("nm0424060"), G.V.get("nm0000243")); 
            G.shortestPathBFS(G.V.get("nm4689420"), G.V.get("nm0000365")); 
            G.shortestPathBFS(G.V.get("nm0000288"), G.V.get("nm0001401")); 
            G.shortestPathBFS(G.V.get("nm0031483"), G.V.get("nm0931324")); 
            System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
    
            System.out.println("<<<<<<OPPGAVE 3>>>>>>\n");
    
            G.chillesteVei(G.V.get("nm2255973"), G.V.get("nm0000460"));
            G.chillesteVei(G.V.get("nm0424060"), G.V.get("nm0000243")); 
            G.chillesteVei(G.V.get("nm4689420"), G.V.get("nm0000365"));
            System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
    
            // System.out.println("<<<<<<OPPGAVE 4>>>>>>\n");
    
            // G.components();
            // System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
        }
    



    static class Edge implements Comparable<Edge>
    {
        Movie movie;
        Actor actor;
        float weight;
        Edge(Movie movie, Actor actor, float weight) 
        { 
            this.movie = movie; this.actor = actor; this.weight = weight; 
        }

        @Override
        public int compareTo(Edge v) 
        {
            if (this.weight > v.weight) { return 1; }
            if (this.weight < v.weight) { return -1; }
            return 0;
        }
        @Override
        public String toString() { return actor.toString(); }
    }


    static class Movie
    {
        String title;
        float rating;
        int votes;
        ArrayList<Actor> actors;

        Movie(String title, float rating, int votes)
        {
            this.title = title;
            this.rating = rating;
            this.votes = votes;
            actors = new ArrayList<>();
        }
        @Override
        public String toString() {
            return title + " | " + rating;
        }
    }
    

    static class Actor
    {
        String name;
        ArrayList<Movie> movies;
        Actor(String name) 
        { 
            this.name = name; 
            movies = new ArrayList<>();
        }
        @Override
        public String toString() { return name ; }
    }
    




    //  mainclass konstruktør

    HashMap<String, Actor> V;
    HashMap<String, Movie> M;
    
    Innleveringsoppgave3 (HashMap<String,Actor> V, HashMap<String,Movie> M)
    {
        this.M = M;
        this.V = V;
    }
    @Override
    public String toString() { return V.toString() + "\n" ; }
    
    


    
    void printGraf()
    {
        System.out.println("Nodes: \t" + V.size());
        int size = 0;
        for (Movie movie : M.values())
        {   
            size = size + ((movie.actors.size() - 1) * movie.actors.size()) / 2;
        }
        System.out.println("Edges: \t" + size + "\n");
    }
    
    
    //  oppgave 2

    void shortestPathBFS(Actor a, Actor b)
    {
        HashMap<Actor,Edge> path = new HashMap<>();
        ArrayList<Edge> queue = new ArrayList<>();
        Edge start = new Edge(null, b, 0f);
        Edge end = start;
        queue.add(start);
        path.put(b, null);
        outer : while (!queue.isEmpty())
        {
            Edge u = queue.remove(0);
            for (Movie x : u.actor.movies) 
            {
                for (Actor v : x.actors)
                {
                    if (path.get(v) == null)
                    {
                        path.put(v, u);
                        Edge yurr = new Edge(x,v, 0f);
                        if (v == a) {end = yurr; break outer;}
                        queue.add(yurr);
                    }
                }   
            }
        }
        //  printer resultetet
        while (end != start)
        {
            System.out.print(end.actor + "\n [" + end.movie + "]\t==>\t");
            end = path.get(end.actor);
        }
        System.out.println(end.actor + "\n");
    }
    
    
    //  oppgave 3

    void chillesteVei(Actor a, Actor b)
    {
        HashMap<Actor,Edge> path = new HashMap<>();
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        Edge start = new Edge(null, b, 0f);
        Edge end = start;
        queue.add(start);
        path.put(b, null);
        outer : while (!queue.isEmpty())
        {
            Edge u = queue.poll();
            if (u.actor == a) { end = u; break outer; }
            
            for (Movie x : u.actor.movies)
            {
                for (Actor v : x.actors)
                {
                    float c = u.weight + (10 - x.rating);
                    if (path.get(v) == null) { path.put(v, new Edge(x, u.actor, Float.MAX_VALUE)); }
                    if (c < path.get(v).weight)
                    {
                        Edge w = path.get(v);
                        w.weight = c;
                        w.movie = x;
                        path.put(v,w);
                        queue.add(new Edge(x,v,c));
                    }
                }
            }
        }
        float tot_weight = end.weight;
        while (end.actor != start.actor)
        {
            System.out.print(end.actor + "\n [" + end.movie + "]\t==>\t");
            end = path.get(end.actor);
        }
        System.out.println(end.actor);
        System.out.print("Total weight: " + String.format("%.2g%n", tot_weight) + "\n"); 
    }


    // //  oppgave 4

    // HashSet<Actor> DFSvisit(Actor s, HashSet<Actor> visited)
    // {
    //     HashSet<Actor> komponent = new HashSet<>();
    //     Stack<Actor> stack = new Stack<>();
    //     stack.push(s);
    //     while (!stack.isEmpty())        
    //     {
    //         Actor u = stack.pop();
    //         if (!visited.contains(u))
    //         {
    //             visited.add(u);
    //             komponent.add(u);
    //             try 
    //             {
    //                 for (Edge v : E.get(u))
    //                 {
    //                     stack.push(v.actor);
    //                 } 
    //             }
    //         catch (NullPointerException e) { /*ingen kant*/ }
    //         }
    //     }       
    //     return komponent;
    // }


    // void components()
    // {
    //     HashSet<Actor> visited = new HashSet<>();
    //     ArrayList<HashSet<Actor>> komponenter = new ArrayList<>();
    //     for (Actor v : V.values())
    //     {
    //         if (!visited.contains(v))
    //         {
    //             HashSet<Actor> komponent = DFSvisit(v, visited);
    //             komponenter.add(komponent);
    //         }
    //     }

    //     //  teller og printer resultatet
        
    //     HashMap<Integer,Integer> sizes = new HashMap<>();
    //     for (HashSet<Actor> komponent : komponenter)
    //     {
    //         sizes.putIfAbsent(komponent.size(), 0);
    //         int size = sizes.get(komponent.size());
    //         sizes.put(komponent.size(), size+1);
    //     }
    //     for (int i : sizes.keySet())
    //     {
    //         System.out.println("There are " + sizes.get(i) + " components of size " + i );
    //     }
    // }

    
    
    
    
    
    
    
    //  representer grafen med naboliste

    static Innleveringsoppgave3 byggGraf(String[] args) throws IOException 
    {
        HashMap<String, Movie> M = new HashMap<>();
        HashMap<String, Actor> V = new HashMap<>();
        // HashMap<Actor,HashSet<Edge>> E = new HashMap<>();

        //  movies.tsv

        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String[] input = br.readLine().strip().split("\t");
        while (true)
        {
        
            Movie v = new Movie(input[1], Float.parseFloat(input[2]), Integer.parseInt(input[3]));
            M.put(input[0], v);
            
            try { input = br.readLine().strip().split("\t"); }
            catch (NullPointerException e) { break; }
        }
        br.close();

        //  actors.tsv

        br = new BufferedReader(new FileReader(args[1]));
        input = br.readLine().strip().split("\t");
        while (true)
        {
            Actor e = new Actor(input[1]);
            for (int i=2; i < input.length; i++)
            {
                try 
                { 
                    M.get(input[i]).actors.add(e);
                    e.movies.add(M.get(input[i]));
                }
                catch (NullPointerException o) { continue; }
            }
            V.put(input[0],e);
            try { input = br.readLine().strip().split("\t"); }
            catch (NullPointerException exc) { break; }
        }
        br.close();

        return new Innleveringsoppgave3(V,M);
    }
}