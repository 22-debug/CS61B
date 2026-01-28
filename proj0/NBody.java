public class NBody{

    public static double readRadius(String file){
        // return the radius of the universe in file
        In in = new In(file);
        in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String file){
        // return an array of planets corresponding to the planet in the file
        In in = new In(file);
        int N = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[N];
        for(int i = 0; i < N; i++) {
            planets[i] = new Planet(in.readDouble(),in.readDouble(),
                    in.readDouble(),in.readDouble(),in.readDouble(),in.readString());
        }
        return planets;
    }

    private static String imageToDraw = "images/starfield.jpg";

    public static void main(String[] args) {

        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double R = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        StdDraw.setScale(-R,R);
        StdDraw.enableDoubleBuffering();
        /*
        StdDraw.picture(0,0,imageToDraw);
        for(Planet planet : planets){
            planet.draw();
        }
        StdDraw.show();
        */
        double t = 0;
        int n = planets.length;
        while(t <= T) {
            StdDraw.clear();
            double[] xForces = new double[n];
            double[] yForces = new double[n];
            for(int i = 0; i < n; i++){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
                //planets[i].update(dt,xForces[i],yForces[i]);
            }
            for(int i = 0; i < n; i++){
                planets[i].update(dt,xForces[i],yForces[i]);
                //Important: For each time through the main loop, do not make any calls to update
                // until all forces have been calculated and safely stored in xForces and yForces.
            }
            StdDraw.picture(0,0,imageToDraw);
            for(Planet planet : planets){
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            t += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", R);
        for (Planet planet : planets) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planet.xxPos, planet.yyPos, planet.xxVel,
                    planet.yyVel, planet.mass, planet.imgFileName);
        }
    }
}