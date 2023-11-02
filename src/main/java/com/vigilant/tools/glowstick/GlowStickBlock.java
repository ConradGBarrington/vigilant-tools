package com.vigilant.tools.glowstick;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class GlowStickBlock extends FacingBlock implements Waterloggable{
  public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
  public static final DirectionProperty FACING = Properties.FACING;
  protected static final VoxelShape BOUNDING_SHAPE_UP = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 10.0, 10.0);
  protected static final VoxelShape BOUNDING_SHAPE_DOWN = Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 16.0, 10.0);
  protected static final VoxelShape BOUNDING_SHAPE_EAST = Block.createCuboidShape(0.0, 6.0, 6.0, 10.0, 10.0, 10.0);
  protected static final VoxelShape BOUNDING_SHAPE_WEST = Block.createCuboidShape(6.0, 6.0, 6.0, 16.0, 10.0, 10.0);
  protected static final VoxelShape BOUNDING_SHAPE_NORTH = Block.createCuboidShape(6.0, 6.0, 6.0, 10.0, 10.0, 16.0);
  protected static final VoxelShape BOUNDING_SHAPE_SOUTH = Block.createCuboidShape(6.0, 6.0, 0.0, 10.0, 10.0, 10.0);

  public GlowStickBlock(Settings settings) {
    super(settings);
    setDefaultState(
      getDefaultState()
        .with(WATERLOGGED, false)
        .with(FACING, Direction.UP)
    );
  }

  @Override
  public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
    Direction facing = state.get(FACING);

    switch (facing.getName()) {
      case "down":
        return BOUNDING_SHAPE_DOWN;
      case "north":
        return BOUNDING_SHAPE_NORTH;
      case "south":
        return BOUNDING_SHAPE_SOUTH;
      case "east":
        return BOUNDING_SHAPE_EAST;
      case "west":
        return BOUNDING_SHAPE_WEST;
      case "up":
      default:
        return BOUNDING_SHAPE_UP;
    }
  }

  @Override
  protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
    builder.add(FACING, WATERLOGGED);
  }

  @Override
  public BlockState getPlacementState(ItemPlacementContext ctx) {
    return (BlockState)this.getDefaultState()
      .with(WATERLOGGED, ctx.getWorld().getFluidState(ctx.getBlockPos()).isOf(Fluids.WATER));
  }

  public BlockState getRemotePlacementState(World world, BlockPos blockPos, Direction direction) {
    return (BlockState)this.getDefaultState()
      .with(WATERLOGGED, world.getFluidState(blockPos).isOf(Fluids.WATER))
      .with(FACING, direction);
  }
  
  @SuppressWarnings("deprecation")
  public FluidState getFluidState(BlockState state) {
    return (Boolean)state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
  }

  @SuppressWarnings("deprecation")
  public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
    if (state.get(WATERLOGGED)) {
        world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
    }

    return direction.getOpposite() == state.get(FACING) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
  }

}
