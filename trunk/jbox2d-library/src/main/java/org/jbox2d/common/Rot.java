/*******************************************************************************
 * Copyright (c) 2011, Daniel Murphy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.jbox2d.common;

import java.io.Serializable;

/**
 * Represents a rotation
 * 
 * @author Daniel
 */
public class Rot implements Serializable {
  private static final long serialVersionUID = 1L;

  private float s, c; // sin and cos

  public Rot() {
    setIdentity();
  }

  public Rot(float angle) {
    set(angle);
  }

  public float getSin() {
    return s;
  }

  public float getCos() {
    return c;
  }

  public Rot set(float angle) {
    s = MathUtils.sin(angle);
    c = MathUtils.cos(angle);
    return this;
  }

  public Rot set(Rot other) {
    s = other.s;
    c = other.c;
    return this;
  }

  public Rot setIdentity() {
    s = 0;
    c = 1;
    return this;
  }

  public float getAngle() {
    return MathUtils.atan2(s, c);
  }

  public void getXAxis(Vec2 xAxis) {
    xAxis.set(c, s);
  }

  public void getYAxis(Vec2 yAxis) {
    yAxis.set(-s, c);
  }

  @Override
  public Rot clone() {
    Rot copy = new Rot();
    copy.s = s;
    copy.c = c;
    return copy;
  }
  
  /**
   * Multiply two rotations
   * NOTE: out cannot be q or r
   * @param q
   * @param r
   * @param out
   */
  public static final void mul(Rot q, Rot r, Rot out) {
    assert(r != out);
    assert(q != out);
    // [qc -qs] * [rc -rs] = [qc*rc-qs*rs -qc*rs-qs*rc]
    // [qs  qc]   [rs  rc]   [qs*rc+qc*rs -qs*rs+qc*rc]
    // s = qs * rc + qc * rs
    // c = qc * rc - qs * rs
    out.s = q.s * r.c + q.c * r.s;
    out.c = q.c * r.c - q.s * r.s;
  }
}