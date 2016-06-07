/*
 * Copyright (c) 2016 acmi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package acmi.l2.clientmod.view;

import acmi.l2.clientmod.unreal.engine.Texture;
import acmi.l2.clientmod.unreal.properties.PropertiesUtil;
import gr.zdimensions.jsquish.Squish;

import java.awt.*;
import java.awt.image.BufferedImage;

import static gr.zdimensions.jsquish.Squish.decompressImage;

public class ImageUtil {
    public static BufferedImage getImage(Texture texture) {
        Format format = Format.values()[(Integer) PropertiesUtil.getAt(texture.properties, "Format").getAt(0)];
        int width = (Integer) PropertiesUtil.getAt(texture.properties, "USize").getAt(0);
        int height = (Integer) PropertiesUtil.getAt(texture.properties, "VSize").getAt(0);

        byte[] data = texture.mipMaps[0].data;

        switch (format) {
            case DXT1:
            case DXT3:
            case DXT5:
                byte[] decompressed = decompressImage(null, width, height, data, Squish.CompressionType.valueOf(format.name()));
                BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
                bi.getRaster().setDataElements(0, 0, width, height, decompressed);
                return bi;
            default:
                throw new UnsupportedOperationException(format.name());
        }
    }

    public static BufferedImage removeAlpha(BufferedImage img, Filler background) {
        BufferedImage copy = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = copy.createGraphics();
        background.fill(g2d, copy.getWidth(), copy.getHeight());
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return copy;
    }

    public static BufferedImage removeAlpha(BufferedImage img) {
        return removeAlpha(img, (g2d, width, height) -> {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
        });
    }

    public interface Filler {
        void fill(Graphics2D g2d, int width, int height);
    }

    private enum Format {
        P8,
        RGBA7,
        RGB16,
        DXT1,
        RGB8,
        RGBA8,
        NODATA,
        DXT3,
        DXT5,
        L8,
        G16,
        RRRGGGBBB
    }
}
