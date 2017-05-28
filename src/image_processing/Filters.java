/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_processing;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 *
 * @author Krzysiek
 */
public class Filters {

    public static float lowPass[] = new float[]{1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9, 1.f / 9};
    public static float highPass[] = new float[]{-1, -1, -1, -1, 9, -1, -1, -1, -1};

    public BufferedImage filter(BufferedImage bi, float[] filter) {
        Kernel kernel;
        kernel = new Kernel(3, 3, filter);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bi = op.filter(bi, null);
        return bi;
    }
}
