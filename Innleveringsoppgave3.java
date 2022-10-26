import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class Innleveringsoppgave3
{
    public static void main(String[] args) throws IOException 
    {
        Innleveringsoppgave3 G = byggGraf(args);
        G.printGraf();  
        // System.out.println("\n" + G.shortestPathBFS(G.V.get("nm0424060"), G.V.get("nm0000243"))); 
        System.out.println("\n" + G.shortestPathBFS(G.V.get("nm2255973"), G.V.get("nm0000460"))); 
        // System.out.println(G.shortestPathBFS(G.V.get("nm0424060"), G.V.get("nm0000243"))); 
    }


    HashMap<String, Movie> M;
    HashMap<String, Actor> V;

    Innleveringsoppgave3 (HashMap<String, Movie> M, HashMap<String, Actor> V)
    {
        this.V = V;
        this.M = M;
    }


    void printGraf()
    {
        System.out.println("Oppgave 1\n");
        System.out.println("Nodes: \t" + V.size());
        int size = 0;
        for (Movie m : M.values())
        {   
            size = size + ((m.actors.size()-1)*m.actors.size())/2;
        }
        System.out.println("Edges: \t" + size);
    }


    HashMap shortestPathBFS(Actor a, Actor b)
    {
        HashSet<Actor> visited = new HashSet<>();
        HashSet<Movie> visitedFilm = new HashSet<>();
        HashMap<Actor,ArrayList<Actor>> parents = new HashMap<>();
        ArrayList<Actor> queue = new ArrayList<>();
        queue.add(a);
        while (!queue.isEmpty())
        {
            Actor u = queue.remove(0);
            if (u == b) {break;}
            if (!visited.contains(u))
            {
                visited.add(u);
                for (Movie x : u.movies) 
                {
                    if (!visitedFilm.contains(x))   // Hindrer at man backtracker
                    {
                        visitedFilm.add(x);
                        for (Actor v : x.actors) 
                        {
                            parents.putIfAbsent(v, new ArrayList<>());
                            parents.get(v).add(u);
                            queue.add(v);
                        }
                    }
                }
            }
        }
        Actor t = b;
        // while (t != a)
        // {
            for (int i = 0; i < parents.get(t).size()+40; i++)
            {
                t = b;
                System.out.println(t);
                t = parents.get(t).get(i);
                System.out.println(t);
                t = parents.get(t).get(0);
                System.out.println(t);
                t = parents.get(t).get(0);
            }
        // }
        return null;
    }

        // ArrayList<Character> queue = new ArrayList<>();
        // HashMap<Character, Character> parents = new HashMap<>();
        // parents.put(s, null);
        // queue.add(s);
        // while (!queue.isEmpty())
        // {
        //     char v = queue.remove(0);
        //     for (char u : G.E.get(v))
        //     {
        //         if (parents.get(u) == null & u != s)
        //         {  
        //             parents.put(u, v);
        //             queue.add(u);
        //         }
        //     }
        // }
        // return parents;


    @Override
    public String toString() { return M.toString() + "\n" ; }


    static class Movie
    {
        String title;
        float rating;
        int votes;
        HashSet<Actor> actors;

        Movie(String title, String rating, String votes)
        {
            this.title = title;
            this.rating = Float.parseFloat(rating);
            this.votes = Integer.parseInt(votes);
            actors = new HashSet<>();
        }

        @Override
        public String toString() {
            return title;
        }
    }
    

    static class Actor
    {
        String name;
        HashSet<Movie> movies;

        Actor(String name)
        {
            this.name = name;
        }

        @Override
        public String toString() {
            return name ;
        }
    }


    static Innleveringsoppgave3 byggGraf(String[] args) throws IOException 
    {
        HashMap<String, Movie> M = new HashMap<>();
        HashMap<String, Actor> V = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(args[0]));
        String[] input = br.readLine().strip().split("\t");
        while (true)
        {
            // Noder
            Movie v = new Movie(input[1], input[2], input[3]);
            M.put(input[0], v);

            try { input = br.readLine().strip().split("\t"); }
            catch (NullPointerException e) { break; }
        }
        br.close();
        br = new BufferedReader(new FileReader(args[1]));
        input = br.readLine().strip().split("\t");
        while (true)
        {
            // Noder
            HashSet<Movie> movies = new HashSet<Movie>();
            Actor e = new Actor(input[1]);
            for (int i=2; i < input.length; i++)
            {
                try { M.get(input[i]).actors.add(e);}
                catch (NullPointerException o) { continue; }
                movies.add(M.get(input[i]));
            }
            e.movies = movies;
            V.put(input[0],e);

            try { input = br.readLine().strip().split("\t"); }
            catch (NullPointerException exc) { break; }
        }
        br.close();
        return new Innleveringsoppgave3(M,V);
    }


}