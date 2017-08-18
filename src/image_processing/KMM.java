package image_processing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public final class KMM {

    private static final int black = Color.black.getRGB(); // 1
    private static final int white = Color.white.getRGB(); // 0
    private static final int red = Color.red.getRGB();		// 3
    private static final int magenta = Color.magenta.getRGB(); // 4
    private static final int green = Color.green.getRGB();	// 2

    private static final class ImageBytes {

        private int width, height;
        private int[] bytes;

        ImageBytes(BufferedImage img) {
            width = img.getWidth();
            height = img.getHeight();
            bytes = new int[(width + 2) * (height + 2)];
            Arrays.fill(bytes, white);
            img.getRGB(0, 0, width, height, bytes, width + 3, width + 2);
        }

        final int width() {
            return width;
        }

        final int height() {
            return height;
        }

        final int at(int x, int y) {
            return bytes[(y + 1) * (width + 2) + (x + 1)];
        }

        final void set(int x, int y, int value) {
            bytes[(y + 1) * (width + 2) + (x + 1)] = value;
        }

        final int count(int x, int y) {
            return ((at(x, y - 1) != white) ? 1 : 0)
                    + ((at(x + 1, y - 1) != white) ? 2 : 0)
                    + ((at(x + 1, y) != white) ? 4 : 0)
                    + ((at(x + 1, y + 1) != white) ? 8 : 0)
                    + ((at(x, y + 1) != white) ? 16 : 0)
                    + ((at(x - 1, y + 1) != white) ? 32 : 0)
                    + ((at(x - 1, y) != white) ? 64 : 0)
                    + ((at(x - 1, y - 1) != white) ? 128 : 0);
        }

        final void save(BufferedImage img) {
            img.setRGB(0, 0, width, height, bytes, width + 3, width + 2);
        }
    }

    private static final boolean[] buildKillsArray(int[] kills) {
        boolean[] ar = new boolean[256];
        Arrays.fill(ar, false);
        for (int i = 0; i < kills.length; ++i) {
            ar[kills[i]] = true;
        }
        return ar;
    }
    private static final boolean[] killsRound = buildKillsArray(new int[]{
        3, 12, 48, 192, 6, 24, 96, 129, //	-	2 sąsiadów
        14, 56, 131, 224, 7, 28, 112, 193, //	-	3 sąsiadów
        195, 135, 15, 30, 60, 120, 240, 225,//	-	4 sąsiadów
    });
    private static final boolean[] killsFinish = buildKillsArray(new int[]{
        5, 13, 15, 20, 21, 22, 23, 29,
        30, 31, 52, 53, 54, 55, 60, 61,
        62, 63, 65, 67, 69, 71, 77, 79,
        80, 81, 83, 84, 85, 86, 87, 88,
        89, 91, 92, 93, 94, 95, 97, 99,
        101, 103, 109, 111, 113, 115, 116, 117,
        118, 119, 120, 121, 123, 124, 125, 126,
        127, 133, 135, 141, 143, 149, 151, 157,
        159, 181, 183, 189, 191, 195, 197, 199,
        205, 207, 208, 209, 211, 212, 213, 214,
        215, 216, 217, 219, 220, 221, 222, 223,
        225, 227, 229, 231, 237, 239, 240, 241,
        243, 244, 245, 246, 247, 248, 249, 251,
        252, 253, 254, 255,
        3, 12, 48, 192, //	-	2 sąsiadów
        14, 56, 131, 224, //	-	3 sąsiadów 'I'
        7, 28, 112, 193, //	-	3 sąsiadów 'L'
    });

    public static final BufferedImage thin(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));
                if((c.getRed() != 0 && c.getRed() != 255) ||
                       (c.getGreen() != 0 && c.getGreen() != 255) ||
                        (c.getBlue() != 0 && c.getBlue() != 255) ||
                        (c.getRed() != c.getGreen() || c.getRed() != c.getBlue() || c.getGreen() != c.getBlue()))
                    throw new RuntimeException("Image is not binary   " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
            }
        }
        ImageBytes bytes = new ImageBytes(img);

        boolean thinned;
        do {
            thinned = true;

            // BORDER
            for (int x = 0; x < bytes.width(); ++x) {
                for (int y = 0; y < bytes.height(); ++y) {
                    if (bytes.at(x, y) != black) {
                        continue;
                    }
                    if (bytes.at(x - 1, y) == white || bytes.at(x + 1, y) == white
                            || bytes.at(x, y - 1) == white || bytes.at(x, y + 1) == white) {
                        bytes.set(x, y, red); // 3
                    } else if (bytes.at(x - 1, y - 1) == white || bytes.at(x + 1, y - 1) == white
                            || bytes.at(x - 1, y + 1) == white || bytes.at(x + 1, y + 1) == white) {
                        bytes.set(x, y, magenta); // 4
                    }
                }
            }

            // ROUND
            for (int x = 0; x < bytes.width(); ++x) {
                for (int y = 0; y < bytes.height(); ++y) {
                    if (bytes.at(x, y) != red) {
                        continue;
                    }
                    if (killsRound[bytes.count(x, y)]) {
                        bytes.set(x, y, green); // 2
                    }
                }
            }

            // CLEAR
            for (int x = 0; x < bytes.width(); ++x) {
                for (int y = 0; y < bytes.height(); ++y) {
                    if (bytes.at(x, y) == green) {
                        bytes.set(x, y, white); // 0
                        thinned = false;
                    }
                }
            }

            // FINISH A
            for (int x = 0; x < bytes.width(); ++x) {
                for (int y = 0; y < bytes.height(); ++y) {
                    if (bytes.at(x, y) != red) {
                        continue;
                    }
                    if (killsFinish[bytes.count(x, y)]) {
                        bytes.set(x, y, white);
                        thinned = false;
                    } else {
                        bytes.set(x, y, black);
                    }
                }
            }

            // FINISH B
            for (int x = 0; x < bytes.width(); ++x) {
                for (int y = 0; y < bytes.height(); ++y) {
                    if (bytes.at(x, y) != magenta) {
                        continue;
                    }
                    if (killsFinish[bytes.count(x, y)]) {
                        bytes.set(x, y, white);
                        thinned = false;
                    } else {
                        bytes.set(x, y, black);
                    }
                }
            }

        } while (!thinned);

        bytes.save(img);
        return img;
    }

}
