package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class FreeCamera {
    public Vector3f linearAcc = new Vector3f();
    public Vector3f linearVel = new Vector3f();

    /** Always rotation about the local XYZ axes of the camera! */
    public Vector3f angularAcc = new Vector3f();
    public Vector3f angularVel = new Vector3f();

    private final Matrix4f view = new Matrix4f();

    /**
     * Update this {@link FreeCamera} based on the given elapsed time.
     * 
     * @param dt
     *            the elapsed time
     * @return this
     */
    public FreeCamera update(float dt) {
        // update linear velocity based on linear acceleration
        linearVel.fma(dt, linearAcc);
        // update angular velocity based on angular acceleration
        angularVel.fma(dt, angularAcc);
        // update the rotation based on the angular velocity
        view.rotateLocalX(dt*angularVel.x)
            .rotateLocalY(dt*angularVel.y)
            .rotateLocalZ(dt*angularVel.z);
        // update position based on linear velocity
        view.translateLocal(-dt*linearVel.x, -dt*linearVel.y, -dt*linearVel.z);
        return this;
    }

    /**
     * Get the current view matrix.
     * 
     * @return the view matrix
     */
    public Matrix4f view() {
        return view;
    }

}