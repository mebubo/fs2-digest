package playground

import Fold.ChunkFold
import fs2.Chunk

object Length {

  def length[A]: ChunkFold[A, Long] = new ChunkFold[A, Long] {
    type I = Long
    def init: I = 0
    def f(i: I, c: Chunk[A]): I = i + c.size
    def end(i: I): Long = i
  }
}