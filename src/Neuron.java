import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;


public class Neuron {

    private final static float DEFAULT_DIAMETER = 30;

    // Positional data
    private PApplet p;
    private float x, y;
    private float diameter;
    private float radius;

    private double value;
    private ArrayList<Edge> connectedToNextLayer;


    Neuron(PApplet pApplet, float x, float y) {
        this.p = pApplet;
        this.x = x;
        this.y = y;
        this.diameter = DEFAULT_DIAMETER;
        this.radius = diameter / 2;
        this.connectedToNextLayer = new ArrayList<>();
    }


    void draw() {
        p.stroke(255);
        p.ellipse(x, y, diameter, diameter);

        for(Edge edge : connectedToNextLayer) {
            p.stroke(edge.getColorWeight());
            p.line(this.x, this.y, edge.connectsTo.getX(), edge.connectsTo.getY());
        }
    }

    void addConnection(Neuron n) {
        connectedToNextLayer.add(new Edge(n));
    }


    // Getters / Setters
    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}


class Edge {
    Neuron connectsTo;
    double weight;

    Edge(Neuron connectsTo) {
        this.weight = Math.random();
        this.connectsTo = connectsTo;
    }

    void updateWeight() {

    }

    double getWeight() {
        return weight;
    }

    int getColorWeight() {
       return (int)Math.round(weight * 255);
    }
}