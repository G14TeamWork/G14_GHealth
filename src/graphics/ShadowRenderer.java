/*
 * @(#)ShadowRenderer.java
 *
 * 2002 - 2012 JIDE Software Incorporated. All rights reserved.
 * Copyright (c) 2005 - 2012 Catalysoft Limited. All rights reserved.
 */
package graphics;

import java.awt.image.BufferedImage;

/**
 * An interface for adding a shadow to an in-memory image
 */
public interface ShadowRenderer {
    /**
     * Creates and returns a shadow image based on the supplied image
     * @param image
     * @return
     */
    public BufferedImage createShadow(final BufferedImage image);
}
