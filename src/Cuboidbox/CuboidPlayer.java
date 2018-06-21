package Cuboidbox;

import javax.media.opengl.GL;

import com.sun.opengl.util.texture.Texture;

public class CuboidPlayer extends Cuboid {
	public static float msfTimeAni = 0.5f;// Time chuyen trang thai
	public enum Action{
		ST, // dung
		LR, // nam ngang 
		UD, // nam doc
	}
	public int mSTT;// 0 BT, -1 : Lose --- 1 :Win 
	public int mX,mZ; // Current pos
	public int mNextX,mNextZ;// Next pos
	CuboidDrawLR mCubeLR;
	CuboidDrawUD mCubeUD;
	public Action mAction,mNextAction;
	public CuboidPlayer(Texture _tex) {
		super(_tex);
		mCubeLR = new CuboidDrawLR(_tex);
		mCubeUD = new CuboidDrawUD(_tex);
		mAction =Action.ST;
		mNextAction = Action.ST;
		mSTT = 0; 
		// TODO Auto-generated constructor stub
	}
	@Override
	public void Update (float deta){
		if(mSTT == 0){ 
			if(iProgress()){
				mProgress+= deta / msfTimeAni; 
				if(mProgress>=1.0f){ 
					mX= mNextX;
					mZ = mNextZ;
					mAction=mNextAction;
					mProgress =1;
					RSSound.get().mAddBrick.play();
				}
			}
		}else{
//			mProgress = 0;
			if(mSTT >0){ // Win
				mPos[1] -= deta*5.0f;
			}else{ // Lose
				mPos[1] -= deta*5.0f;
			}
		}
	}
	public void SetWin() {
		mProgress = 0;
		mSTT = 1;
	}
	public void SetLose() {
		mProgress = 0;
		mSTT = -1;
	}
	@Override
	public void Draw (GL gl){
		if(!iProgress()){
			mPos[0] = mX * 2;
			mPos[2] = mZ * 2;
			switch(mAction){
			case ST:
				gl.glPushMatrix();
					gl.glScalef(1, 2, 1);
					gl.glTranslatef(0, 1, 0);
					gl.glTranslatef(mPos[0], mPos[1],  mPos[2]);
					mCubeDraw.Draw(gl);
				gl.glPopMatrix();
				break;
			case LR:
				gl.glPushMatrix();
					gl.glTranslatef(1, 1, 0);	
					gl.glScalef(2, 1, 1);
					gl.glTranslatef(mPos[0]/2.0f, mPos[1],  mPos[2]);
					mCubeDraw.Draw(gl);
				gl.glPopMatrix();
				break;
			case UD:
				gl.glPushMatrix();
					gl.glTranslatef(0, 1, 1);
					gl.glScalef(1, 1, 2);
					gl.glTranslatef(mPos[0], mPos[1],  mPos[2]/2.0f);
					mCubeDraw.Draw(gl);
				gl.glPopMatrix();
				break;
			}
		}
		else{
			switch(mAction){
			case ST:
				if(mNextAction== Action.UD){ 
					float Angle = 90*(1.0f-mProgress);
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextZ>mZ){
						Angle = 90*(mProgress);
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1, mPos[1],  mPos[2]+1);
						gl.glRotatef(Angle-90, 1, 0, 0);
						gl.glScalef(1, 1, -1);
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}else{
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1, mPos[1],  mPos[2]-1);
						gl.glRotatef(Angle, 1, 0, 0);
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}
				}else{// Trang thai LR
					float Angle = 90*(1.0f-mProgress);// goc xoay tu 0-90
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextX>mX){
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]+1.0f, mPos[1],  mPos[2]+1.0f);
						gl.glRotatef(Angle, 0, 0, 1);
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}else{
						Angle = 90*(mProgress);
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]+1.0f);
						gl.glRotatef(Angle-90, 0, 0, 1);
						gl.glScalef(-1, 1, 1);	
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}
				}			
				break;
			case LR:
				if(mNextAction== Action.ST){ 
					float Angle = 90*(mProgress);// goc xoay tu 0-90
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextX > mX){
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]+3.0f, mPos[1],  mPos[2]+1.0f);
						gl.glScalef(-1, 1, 1);					
						gl.glRotatef(Angle, 0, 0, 1);					
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}else{
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]+1.0f);
						gl.glRotatef(Angle, 0, 0, 1);					
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}
				}else{
					float Angle = 90*(mProgress);
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextZ > mZ){
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]+1.0f);
						gl.glRotatef(Angle, 1, 0, 0);					
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}else{
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]-1.0f);
						gl.glScalef(1, 1, -1);					
						gl.glRotatef(Angle, 1, 0, 0);					
						mCubeLR.Draw(gl);
						gl.glPopMatrix();
					}
				}
				break;
			case UD:
				if(mNextAction== Action.ST){ 
					float Angle = 90*(mProgress);
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextZ > mZ){
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]+3.0f);
						gl.glRotatef(Angle, 1, 0, 0);					
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}else{
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1, mPos[1],  mPos[2]-1.0f);
						gl.glScalef(1, 1, -1);					
						gl.glRotatef(Angle, 1, 0, 0);					
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}
				}else{
					float Angle = 90*(mProgress);
					
					mPos[0] = mX * 2;
					mPos[2] = mZ * 2;
					if(mNextX > mX){
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]+1.0f, mPos[1],  mPos[2]+3.0f);
						gl.glScalef(-1, 1, 1);
						gl.glRotatef(Angle, 0, 0, 1);					
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}else{
						gl.glPushMatrix();
						gl.glTranslatef(mPos[0]-1.0f, mPos[1],  mPos[2]-1.0f);
						gl.glScalef(1, 1, -1);					
						gl.glRotatef(Angle, 0, 0, 1);					
						mCubeUD.Draw(gl);
						gl.glPopMatrix();
					}
				}
				break;
			}
		}
		
	}
	/*
	 */
	public boolean iProgress (){
		return (mProgress < 1.0f);
	}
	public void SetNextUp (){
		if ( !iProgress()){ 
			RSSound.get().mChose.play();
			mProgress = 0;
			switch(mAction){
			case ST:
				mNextZ = mZ-2;
				mNextAction = Action.UD;
				break;
			case LR:
				mNextZ = mZ-1;
				
				break;
			case UD:
				mNextZ = mZ-1;
				mNextAction = Action.ST;
				break;
			}
		}
	}
	public void SetNextDown (){
		if ( !iProgress()){ 
			RSSound.get().mChose.play();
			mProgress = 0;
			switch(mAction){
			case ST:
				mNextZ = mZ+1;
				mNextAction = Action.UD;
				break;
			case LR:
				mNextZ = mZ+1;
				
				break;
			case UD:
				mNextZ = mZ+2;
				mNextAction = Action.ST;
				break;
			}
		}
	}
	public void SetNextLeft (){
		if ( !iProgress()){ 
			RSSound.get().mChose.play();
			mProgress = 0;
			switch(mAction){
			case ST:
				mNextX = mX-2;
				mNextAction = Action.LR;
				break;
			case LR:
				mNextX = mX-1;
				mNextAction = Action.ST;	
				break;
			case UD:
				mNextX = mX-1;
				
				break;
			}
		}
	}
	public void SetNextRight (){
		if ( !iProgress()){ 
			RSSound.get().mChose.play();
			mProgress = 0;
			switch(mAction){
			case ST:
				mNextX = mX+1;
				mNextAction = Action.LR;
				break;
			case LR:
				mNextX = mX+2;
				mNextAction = Action.ST;	
				break;
			case UD:
				mNextX = mX+1;
				
				break;
			}
		}
		
	}

}
