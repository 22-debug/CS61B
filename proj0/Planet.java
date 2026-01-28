public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    public Planet(double xP,double yP,double xV,double yV,double m,String img){
        this.xxPos = xP;
        this.yyPos = yP;
        this.xxVel = xV;
        this.yyVel = yV;
        this.mass = m;
        this.imgFileName = img;
    }

    public Planet(Planet p){
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    public double calcDistance(Planet p){
        // calculate the distance between this and p
        double dx = p.xxPos - this.xxPos;
        double dy = p.yyPos - this.yyPos;
        return Math.sqrt(dx*dx+dy*dy);
    }

    private static final double G = 6.67e-11;
    public double calcForceExertedBy(Planet p){
        // calculate the force exerted on this by p
        double r = this.calcDistance(p);
        return G*this.mass*p.mass/(r*r);
    }

    public double calcForceExertedByX(Planet p){
        // return the force in the X direction
        double f = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dx = p.xxPos - this.xxPos;
        return f*dx/r;
    }

    public double calcForceExertedByY(Planet p){
        // return the force in the Y direction
        double f = this.calcForceExertedBy(p);
        double r = this.calcDistance(p);
        double dy = p.yyPos - this.yyPos;
        return f*dy/r;
    }

    public double calcNetForceExertedByX(Planet[] p){
        // calc the net X force exerted by all planets in p
        double net_forceX = 0;
        for (Planet planet : p) {
            if(this.equals(planet))
                continue;
            net_forceX += this.calcForceExertedByX(planet);
        }
        return net_forceX;
    }

    public double calcNetForceExertedByY(Planet[] p){
        // calc the net Y force exerted by all planets in p
        double net_forceY = 0;
        for (Planet planet : p) {
            if(this.equals(planet))
                continue;
            net_forceY += this.calcForceExertedByY(planet);
        }
        return net_forceY;
    }

    public void update(double dt,double fx,double fy){
        //update the state of this planet under the affect of fx and fy during the time of dt
        double ax = fx/this.mass;
        double ay = fy/this.mass;
        this.xxVel += dt*ax;
        this.yyVel += dt*ay;
        this.xxPos += dt*this.xxVel;
        this.yyPos += dt*this.yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
    }
}