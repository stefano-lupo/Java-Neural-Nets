import processing.core.PApplet;

import java.util.ArrayList;


public class Neuron {

    public final static float DEFAULT_DIAMETER = 80;
    private static int nextNeuronID = 0;

    // Positional data
    private PApplet p;
    private float x, y;
    private float diameter;
    private float radius;

    private int id;
    private double value;
    private ArrayList<Edge> outgoingEdges;
    private ArrayList<Edge> incomingEdges;


    Neuron(PApplet pApplet, float x, float y) {
        this.p = pApplet;
        this.x = x;
        this.y = y;
        this.diameter = DEFAULT_DIAMETER;
        this.radius = diameter / 2;
        this.outgoingEdges = new ArrayList<>();
        this.incomingEdges = new ArrayList<>();
        this.id = nextNeuronID++;
    }

    void addOutgoingConnection(Neuron n) {
        outgoingEdges.add(new Edge(n));
    }

    void addIncomingConnection(Neuron n) {
        incomingEdges.add(new Edge(n));
    }


    void updateValue() {
        this.value = 0;
        for(Edge edge : incomingEdges) {
            this.value += edge.weight * edge.connectsTo.value;
        }

        sigmoid();
    }

    private void sigmoid() {
        this.value = 1 / (1+Math.exp(-this.value));
    }


    void printConnections() {
        for(Edge edge : outgoingEdges) {
            System.out.println("OUT: " + this.id + " -> " + edge.connectsTo.getId());
        }

        for(Edge edge : incomingEdges) {
            System.out.println("IN: " + this.id + " <- " + edge.connectsTo.getId());
        }

        System.out.println();
    }






    void draw() {
        p.fill(255);
        for(Edge edge : outgoingEdges) {
            edge.draw(p, this);
        }

        p.stroke(255);
        p.ellipse(x, y, diameter, diameter);
        p.fill(0);
        p.textSize(15);
        p.text(String.valueOf(this.id), (float)(x-0.3*DEFAULT_DIAMETER), (float)(y-0.25*DEFAULT_DIAMETER));

        p.textSize(22);
        String valueStr = String.valueOf(this.value);
        p.text(valueStr.substring(0,Math.min(valueStr.length(), 4)), (float)(x-0.1*DEFAULT_DIAMETER), y);


    }




    // Getters / Setters
    float getX() {
        return this.x;
    }

    float getY() {
        return this.y;
    }

    int getId() {
        return this.id;
    }

    double getValue() { return this.value; }

    void setValue(double value) {
        this.value = value;
    }
}


class Edge {
    Neuron connectsTo;
    float weight;

    Edge(Neuron connectsTo) {
        this.weight = (float)Math.random();
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

    void draw(PApplet p, Neuron root) {
        p.stroke(getColorWeight());
        p.line(root.getX(), root.getY(), connectsTo.getX(), connectsTo.getY());
        p.textSize(12);
        String weightStr = String.valueOf(weight);
        float x = (float) (root.getX() + (connectsTo.getX() - root.getX())*0.3);
        float y = (float) (root.getY() + (connectsTo.getY() - root.getY())*0.3);
        p.text(weightStr.substring(0, Math.min(3, weightStr.length())), x, y);
    }
}