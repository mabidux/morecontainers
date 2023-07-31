package dev.abidux.morecontainers.block.custom;

import dev.abidux.morecontainers.block.custom.entity.SwordHolderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.Nullable;

public class SwordHolderBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE_NS = Block.box(1, 0, 5, 15, 4, 11);
    private static final VoxelShape SHAPE_WE = Block.box(5, 0, 1, 11, 4, 15);
    public SwordHolderBlock() {
        super(Properties.of().noOcclusion().mapColor(MapColor.COLOR_GRAY).strength(3.5f).requiresCorrectToolForDrops());
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
        ItemStack stack = player.getItemInHand(hand);
        boolean isSword = stack.getItem() instanceof SwordItem;
        if (!level.isClientSide() && level.getBlockEntity(pos) instanceof SwordHolderBlockEntity entity) {
            entity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
                ItemStack previousStack = handler.getStackInSlot(0);
                if (isSword) {
                    ((IItemHandlerModifiable)handler).setStackInSlot(0, stack.copy());
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }
                if (player.getItemInHand(hand).isEmpty()) {
                    ((IItemHandlerModifiable)handler).setStackInSlot(0, stack.copy());
                    player.setItemInHand(hand, previousStack);
                }
                entity.update();
            });
        }
        if (isSword) return InteractionResult.sidedSuccess(level.isClientSide());
        return super.use(state, level, pos, player, hand, pHit);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            if (pLevel.getBlockEntity(pPos) instanceof SwordHolderBlockEntity entity) {
                entity.remove();
            }
            super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
        }
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return switch (pState.getValue(FACING)) {
            case NORTH, SOUTH -> SHAPE_NS;
            default -> SHAPE_WE;
        };
    }

    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SwordHolderBlockEntity(pPos, pState);
    }
}