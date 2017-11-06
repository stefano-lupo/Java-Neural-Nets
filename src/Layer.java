import processing.core.PApplet;

import java.util.ArrayList;


public class Layer {

    public final static float DEFAULT_LAYER_X_SPACING = 150;
    public final static float DEFAULT_NEURON_Y_SPACING = 50;
    public final static float DEFAULT_VERTICAL_PADDING = 50;


    private PApplet p;
    private ArrayList<Neuron> neurons;
    private int numNeuronsInLayer = 0;

    // display info
    private float layerIndex;
    private float xSpacing = DEFAULT_LAYER_X_SPACING;
    private static int canvasWidth;
    private static int canvasHeight;

    Layer(PApplet p, float layerIndex, int width, int height) {
        this.p = p;
        this.neurons = new ArrayList<>();
        this.layerIndex = layerIndex;
        canvasWidth = width;
        canvasHeight = height;
    }

    Neuron addNeuron() {
        Neuron neuron = new Neuron(p, getBaseXPos() + xSpacing / 2, getNeuronYPos(numNeuronsInLayer));
        neurons.add(neuron);
        numNeuronsInLayer++;
        return neuron;
    }

    void connectAllNeuronsTo(Neuron neuron) {
        for(Neuron n : neurons) {
            n.addConnection(neuron);
        }
    }

    void drawGridLines() {
        p.stroke(255);
        p.line(getBaseXPos(), 0, getBaseXPos(), canvasHeight);
    }

    void drawLayer() {
        if(numNeuronsInLayer == 0) {
            return;
        }

        for(Neuron neuron : neurons) {
            neuron.draw();
        }
    }

    private float getBaseXPos() {
        return this.layerIndex * xSpacing;
    }

    private float getNeuronYPos(int index) {
        return getNeuronHeight() * index + DEFAULT_VERTICAL_PADDING;
    }

    private float getNeuronHeight() {
        return DEFAULT_NEURON_Y_SPACING;
    }
}
