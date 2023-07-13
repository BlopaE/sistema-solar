package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Line;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

public class SistemaSolar extends SimpleApplication {

    private static final float[] ORBIT_SPEEDS = {0.5f, 0.8f, 1.2f, 1.5f, 0.3f, 0.6f, 0.9f, 1.1f}; // Velocidades de órbita (ajustable)
    private static final float[] ROTATION_SPEEDS = {2f, 3f, 1.5f, 1f, 0.5f, 0.8f, 1.2f, 1.4f}; // Velocidades de rotación (ajustable)

    public static void main(String[] args) {
        SistemaSolar app = new SistemaSolar();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        // Configuración de la cámara
        cam.setLocation(new Vector3f(0, 10, 30));
        cam.lookAt(Vector3f.ZERO, Vector3f.UNIT_Y);

        // Luz direccional para iluminar los planetas
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-1, -1, -1));
        rootNode.addLight(sun);

        // Creación del sol
        Geometry sol = crearPlaneta("Sol", 10, "sol.jpg");
        rootNode.attachChild(sol);

        // Creación de los planetas
        Geometry mercurio = crearPlaneta("Mercurio", 1, "mercurio.jpg");
        Geometry venus = crearPlaneta("Venus", 1.5f, "venus.jpg");
        Geometry tierra = crearPlaneta("Tierra", 2, "tierra.jpg");
        Geometry marte = crearPlaneta("Marte", 1.5f, "marte.jpg");
        Geometry jupiter = crearPlaneta("Jupiter", 4, "jupiter.jpg");
        Geometry saturno = crearPlaneta("Saturno", 3.5f, "saturno.jpg");
        Geometry urano = crearPlaneta("Urano", 2.5f, "urano.jpg");
        Geometry neptuno = crearPlaneta("Neptuno", 2.5f, "neptuno.jpg");
        
        // Textura de los planetas
        

        // Posicionamiento de los planetas
        mercurio.setLocalTranslation(15, 0, 0);
        venus.setLocalTranslation(20, 0, 0);
        tierra.setLocalTranslation(25, 0, 0);
        marte.setLocalTranslation(30, 0, 0);
        jupiter.setLocalTranslation(40, 0, 0);
        saturno.setLocalTranslation(50, 0, 0);
        urano.setLocalTranslation(60, 0, 0);
        neptuno.setLocalTranslation(70, 0, 0);

        // Creación de las órbitas
        Node orbitasNode = new Node("Orbitas");
        rootNode.attachChild(orbitasNode);

        crearOrbita(orbitasNode, 15);
        crearOrbita(orbitasNode, 20);
        crearOrbita(orbitasNode, 25);
        crearOrbita(orbitasNode, 30);
        crearOrbita(orbitasNode, 40);
        crearOrbita(orbitasNode, 50);
        crearOrbita(orbitasNode, 60);
        crearOrbita(orbitasNode, 70);

        // Adjuntar planetas al nodo raíz
        rootNode.attachChild(mercurio);
        rootNode.attachChild(venus);
        rootNode.attachChild(tierra);
        rootNode.attachChild(marte);
        rootNode.attachChild(jupiter);
        rootNode.attachChild(saturno);
        rootNode.attachChild(urano);
        rootNode.attachChild(neptuno);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // Obtener el tiempo transcurrido desde la última actualización del cuadro
        float time = timer.getTimeInSeconds();

        // Hacer que los planetas giren alrededor del sol con velocidades diferentes
        rotatePlanet(spatial("Mercurio"), 15, time, ORBIT_SPEEDS[0], ROTATION_SPEEDS[0]);
        rotatePlanet(spatial("Venus"), 20, time, ORBIT_SPEEDS[1], ROTATION_SPEEDS[1]);
        rotatePlanet(spatial("Tierra"), 25, time, ORBIT_SPEEDS[2], ROTATION_SPEEDS[2]);
        rotatePlanet(spatial("Marte"), 30, time, ORBIT_SPEEDS[3], ROTATION_SPEEDS[3]);
        rotatePlanet(spatial("Jupiter"), 40, time, ORBIT_SPEEDS[4], ROTATION_SPEEDS[4]);
        rotatePlanet(spatial("Saturno"), 50, time, ORBIT_SPEEDS[5], ROTATION_SPEEDS[5]);
        rotatePlanet(spatial("Urano"), 60, time, ORBIT_SPEEDS[6], ROTATION_SPEEDS[6]);
        rotatePlanet(spatial("Neptuno"), 70, time, ORBIT_SPEEDS[7], ROTATION_SPEEDS[7]);
    }

    private Geometry crearPlaneta(String nombre, float radio, String filename) {
        Sphere sphere = new Sphere(32, 32, radio);
        Geometry planet = new Geometry(nombre, sphere);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        Texture texture = assetManager.loadTexture("resources/"+filename);
        mat.setTexture("ColorMap", texture);
        planet.setMaterial(mat);
        return planet;
    }

    private void crearOrbita(Node parentNode, float radio) {
        Line orbita = new Line(new Vector3f(radio, 0, 0), new Vector3f(-radio, 0, 0));
        Geometry orbitaGeom = new Geometry("Orbita", orbita);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        orbitaGeom.setMaterial(mat);
        parentNode.attachChild(orbitaGeom);
    }

    private void rotatePlanet(Geometry planet, float orbitRadius, float time, float orbitSpeed, float rotationSpeed) {
        float angle = time * orbitSpeed; // Ángulo de rotación basado en el tiempo y la velocidad de la órbita
        float x = FastMath.cos(angle) * orbitRadius; // Coordenada X de la posición del planeta
        float z = FastMath.sin(angle) * orbitRadius; // Coordenada Z de la posición del planeta
        planet.setLocalTranslation(x, 0, z);

        // Rotación sobre el propio eje
        Quaternion rotation = new Quaternion();
        rotation.fromAngleAxis(time * rotationSpeed, Vector3f.UNIT_Y);
        planet.setLocalRotation(rotation);
    }

    private Geometry spatial(String name) {
        return (Geometry) rootNode.getChild(name);
    }
}
