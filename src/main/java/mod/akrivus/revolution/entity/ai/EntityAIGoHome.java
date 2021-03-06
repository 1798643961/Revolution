package mod.akrivus.revolution.entity.ai;

import mod.akrivus.revolution.entity.EntityHuman;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIGoHome extends EntityAIBase {
    protected EntityHuman human;
    protected BlockPos home;
    public EntityAIGoHome(EntityHuman human) {
        this.human = human;
        this.setMutexBits(1);
    }
    @Override
    public boolean shouldExecute() {
    	if (!this.human.getTribe().isHomeless() && !this.human.isSleeping() && this.human.getRevengeTarget() == null) {
    		this.home = this.human.getTribe().getHome();
    		if ((this.human.world.getWorldTime() % 24000) > 12000) {
    			return this.home.getDistance((int)(this.human.posX), (int)(this.human.posY), (int)(this.human.posZ)) > 4;
    		}
    	}
    	return false;
    }
    @Override
    public boolean shouldContinueExecuting() {
    	return false;
    }
    @Override
    public void startExecuting() {
    	if (this.human.getDistanceSq(this.home) > 256) {
    		Vec3d pos = RandomPositionGenerator.findRandomTargetBlockTowards(this.human, 16, 4, new Vec3d(this.home.getX(), this.home.getY(), this.home.getZ()));
            if (pos != null) {
                this.human.getNavigator().tryMoveToXYZ(pos.x, pos.y, pos.z, 1.0D);
            }
    	}
    	else {
    		this.human.getNavigator().tryMoveToXYZ(this.home.getX(), this.home.getY(), this.home.getZ(), 1.0D);
    	}
    }
    @Override
    public void resetTask() {
    	this.home = null;
    }
}