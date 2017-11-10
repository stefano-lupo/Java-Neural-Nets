import processing.core.PApplet;
import java.util.ArrayList;

public class Network extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Network", args);
    }


    private float layerSpacing = Layer.DEFAULT_LAYER_X_SPACING;
    private ArrayList<Layer> layers;
    private boolean settingUp = true;


    private int currentStepLayer = 1;
    private int currentInstance = 0;
    private double[][] values;
    private double[][] outputs;

    public void settings() {
        size(800,800);
    }

    public void setup() {
        layers = new ArrayList<>();
        addLayer();
        addNeuron(0);
        addLayer();
        addNeuron(1);
        addNeuron(1);
        addLayer();

        double[][] values = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1},
        };
        this.values = values;

        double[][] outputs = {
            {0},
            {1},
            {1},
            {0},
        };

        this.outputs = outputs;

        printNetworkConnections();
        feedNetwork();
    }

    public void draw() {
        background(66, 134, 244);

        if(settingUp) {
            drawSetUp();
        } else {
            drawRunning();
        }

    }

    private void drawSetUp() {
        frameRate(10);
        drawNetwork();
        text("Setup",this.width-150, this.height - 50);

        if(mousePressed) {
            int layerIndex = (int)Math.floor(mouseX / layerSpacing);
            if(layerIndex < layers.size() && layers.get(layerIndex) != null) {
                addNeuron(layerIndex);
            } else {
                addLayer();
            }
        }

        if(keyPressed && key=='s') {
            this.settingUp = false;
        }
    }

    private void drawNetwork() {
        for(Layer layer : layers) {
            layer.drawGridLines();
            layer.drawLayer();
        }
    }

    private void addNeuron(int layerIndex) {
        Neuron neuron = layers.get(layerIndex).addNeuron();

        Layer layer;
        if(layerIndex > 0 && (layer = layers.get(layerIndex - 1)) != null) {
            layer.addOutgoingConnection(neuron);
        }

        if(layerIndex < layers.size() - 1 && (layer = layers.get(layerIndex+1)) != null) {
            layer.addIncomingConnection(neuron);
        }
    }

    private void addLayer() {
        int newLayerIndex = layers.size();
        Layer layer = new Layer(this, newLayerIndex, width, height);
        layers.add(layer);
        addNeuron(newLayerIndex);
    }

    private void feedNetwork() {
        layers.get(0).feedLayer(values[currentInstance]);
    }


    private void drawRunning() {
        drawNetwork();
        text("Running",this.width-150, this.height - 50);
        if(mousePressed && currentInstance != values.length) {
            stepNetwork();
        }
    }

    private void stepNetwork() {
        System.out.println("Stepping layer");
        layers.get(currentStepLayer).stepLayer();
        currentStepLayer++;
        if(currentStepLayer == layers.size()) {
            backPropogate();
            currentStepLayer = 1;
            feedNetwork();
        }
    }

    private void backPropogate() {
        double[] target = outputs[currentInstance];
        double[] predicted = layers.get(layers.size()-1).getLayerNeuronValues();

        double[] differences = new double[target.length];
        for(int i=0; i<target.length; i++) {
            differences[i] = target[i] - predicted[i];
        }

        currentInstance++;
    }

    private void printNetworkConnections() {
        for(Layer l : layers) {
            l.printConnections();
        }
    }
}
