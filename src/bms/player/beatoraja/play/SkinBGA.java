package bms.player.beatoraja.play;

import bms.player.beatoraja.Config;
import bms.player.beatoraja.MainState;
import bms.player.beatoraja.PlayerResource;
import bms.player.beatoraja.PlayerResource.PlayMode;
import bms.player.beatoraja.skin.Skin.SkinObjectRenderer;
import bms.player.beatoraja.skin.SkinObject;
import bms.player.beatoraja.skin.StretchType;

import static bms.player.beatoraja.skin.SkinProperty.*;

/**
 * BGAオブジェクト
 * 
 * @author exch
 */
public class SkinBGA extends SkinObject {
	
	private BMSPlayer player;
	private long time;

	public SkinBGA(int bgaExpand) {
		switch (bgaExpand) {
		case Config.BGAEXPAND_FULL:
			setStretch(StretchType.STRETCH);
			break;
		case Config.BGAEXPAND_KEEP_ASPECT_RATIO:
			setStretch(StretchType.KEEP_ASPECT_RATIO_FIT_INNER);
			break;
		case Config.BGAEXPAND_OFF:
			setStretch(StretchType.KEEP_ASPECT_RATIO_NO_EXPANDING);
			break;
		}
	}
	
	@Override
	public void prepare(long time, MainState state) {
		if(player == null) {
			player = (BMSPlayer)state;
		}
		this.time = time;
		super.prepare(time, state);
	}

	public void draw(SkinObjectRenderer sprite) {
		final PlayerResource resource = player.main.getPlayerResource();
		if (resource.getPlayMode() == PlayMode.PRACTICE) {
			player.getPracticeConfiguration().draw(region, sprite, time, player);
		} else if (resource.getBGAManager() != null) {
			final int s = player.getState();
			resource.getBGAManager().drawBGA(
					this,
					sprite,
					region,
					s == BMSPlayer.STATE_PRELOAD || s == BMSPlayer.STATE_PRACTICE || s == BMSPlayer.STATE_READY ? -1
							: player.main.getNowTime(TIMER_PLAY));
		}		
	}

	@Override
	public void draw(SkinObjectRenderer sprite, long time, MainState state) {
		draw(sprite);
	}

	@Override
	public void dispose() {

	}
}
