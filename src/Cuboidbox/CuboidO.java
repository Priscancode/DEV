package Cuboidbox;

import com.sun.opengl.util.texture.Texture;

public class CuboidO extends CuboidNormal{

	public CuboidO(Texture _tex) {
		super(_tex);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int LinkST (){
		System.out.println("LinkST");
		if(mCubeLink != null){
			System.out.println("LinkSTActive");
			mCubeLink.ActionLink();
		}
		return 0;
	}
	
	@Override
	public int Link (){
		System.out.println("Link");
		if(mCubeLink != null){
			mCubeLink.ActionLink();
		}
		return 0;
	}
}
