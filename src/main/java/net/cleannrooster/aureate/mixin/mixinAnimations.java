package net.cleannrooster.aureate.mixin;

import net.cleannrooster.aureate.items.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class mixinAnimations{
    private Hand arm(AbstractClientPlayerEntity player) {
        return Hand.MAIN_HAND;
    }
    /*private void applyEquipOffset(MatrixStack matrices, Arm arm, float equipProgress) {
        int i = arm == Arm.RIGHT ? 1 : -1;
        matrices.translate((float)i, -0.52f + equipProgress * -0.6f, -0.72f);
    }*/
    @Inject(at = @At("HEAD"), method = "renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    private void renderFirstPersonItem(AbstractClientPlayerEntity player, float tickDelta, float pitch, Hand hand, float swingProgress, ItemStack item, float equipProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){
        if ((player.getOffHandStack().getItem() == ModItems.GAUNTLET && hand == Hand.OFF_HAND) || (player.getMainHandStack().getItem() == ModItems.GAUNTLET && hand == Hand.MAIN_HAND)){
            if((player.isUsingRiptide())){
                matrices.translate(1000000f, -0.2f, -0.2f);
         /*       matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(65));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));*/
            }
            /*if (player.isUsingItem() && player.getItemUseTimeLeft() > 0 && player.getActiveHand() == hand) {
                int bl32 = bl2 ? 1 : -1;
                matrices.translate((float)bl32 * -0.2785682f, 0.18344387412071228, 0.15731531381607056);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-13.935f));
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((float)bl32 * 35.3f));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)bl32 * -9.785f));
                float i = (float)item.getMaxUseTime() - ((float)player.getItemUseTimeLeft() - tickDelta + 1.0f);
                float f = i / 20.0f;
                f = (f * f + f * 2.0f) / 3.0f;
                if (f > 1.0f) {
                    f = 1.0f;
                }
                if (f > 0.1f) {
                    float g = MathHelper.sin((i - 0.1f) * 1.3f);
                    float h = f - 0.1f;
                    float j = g * h;
                    matrices.translate(j * 0.0f, j * 0.004f, j * 0.0f);
                }
                matrices.translate(f * 0.0f, f * 0.0f, f * 0.04f);
                matrices.scale(1.0f, 1.0f, 1.0f + f * 0.2f);
                matrices.multiply(Vec3f.NEGATIVE_Y.getDegreesQuaternion((float)bl32 * 45.0f));
    }*/
        }
    }
}
