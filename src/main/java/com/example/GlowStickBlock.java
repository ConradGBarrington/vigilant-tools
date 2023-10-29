package com.example;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class GlowStickBlock extends Block {
  protected static final VoxelShape BOUNDING_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);

  public GlowStickBlock(Settings settings) {
    super(settings);
  }

  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
      return BOUNDING_SHAPE;
   }
  
}
