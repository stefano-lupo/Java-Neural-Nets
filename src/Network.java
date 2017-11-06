import processing.core.PApplet;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Network extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Network", args);
    }


    private float layerSpacing = Layer.DEFAULT_LAYER_X_SPACING;
    private ArrayList<Layer> layers;
    private boolean settingUp = true;

    public void settings() {
        size(800,800);
    }

    public void setup() {
        layers = new ArrayList<>();
//        layers.add(new Layer(this, 0, width, height));

    }

    public void draw() {
        background(66, 134, 244);

        if(settingUp) {
            drawSetUp();
        }

    }

    private void drawSetUp() {
        frameRate(15);
        for(Layer layer : layers) {
            layer.drawGridLines();
            layer.drawLayer();
        }

        if(mousePressed) {
            int layerIndex = (int)Math.floor(mouseX / layerSpacing);
            if(layerIndex < layers.size() && layers.get(layerIndex) != null) {
                addNeuron(layerIndex);
            } else {
                addLayer();
            }
        }
    }

    private void addNeuron(int layerIndex) {
        Neuron neuron = layers.get(layerIndex).addNeuron();

        Layer layer;
        if(layerIndex > 0 && (layer = layers.get(layerIndex - 1)) != null) {
            layer.connectAllNeuronsTo(neuron);
        }

        if(layerIndex < layers.size() - 1 && (layer = layers.get(layerIndex+1)) != null) {
            layer.connectAllNeuronsTo(neuron);
        }
    }

    private void addLayer() {
        int newLayerIndex = layers.size();
        Layer layer = new Layer(this, newLayerIndex, width, height);
        layers.add(layer);
        addNeuron(newLayerIndex);
    }

    private void drawRunning() {

    }
}
