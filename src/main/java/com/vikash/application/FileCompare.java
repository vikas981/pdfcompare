package com.vikash.application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.vikash.utility.FileUtils;

public class FileCompare {
	
	 static ImageComparison imageComparison;
	
	public static void main(String[] args) {
		FileUtils.deleteAllHtml();
		List<String> dir1=FileUtils.getFileName("/home/viksingh/snap/skype/common/TB1/Reports/screenshots");
		List<String> dir2=FileUtils.getFileName("/home/viksingh/snap/skype/common/TB2/Reports/screenshots");
		for(String s:dir1) {
			if(dir2.contains(s)) {
			BufferedImage expectedImage = ImageComparisonUtil.readImageFromResources("/home/viksingh/snap/skype/common/TB1/Reports/screenshots/"+s);
            BufferedImage actualImage = ImageComparisonUtil.readImageFromResources("/home/viksingh/snap/skype/common/TB2/Reports/screenshots/"+s);
    
            File resultDestination = new File("Result/"+s);
            float diffrent=ImageComparisonUtil.getDifferencePercent(expectedImage,actualImage);
            float similar=100-diffrent;
            System.out.println(s +" ------> "+similar);
    
        
            imageComparison = new ImageComparison( expectedImage, actualImage, resultDestination );
    
         
            new ImageComparison(expectedImage, actualImage);
           
    
            //Threshold - it's the max distance between non-equal pixels. By default it's 5.
            imageComparison.setThreshold(5);
            imageComparison.getThreshold();
    
         
            imageComparison.setRectangleLineWidth(4);
            imageComparison.getRectangleLineWidth();
    
            //DifferenceRectangleFilling - Fill the inside the difference rectangles with a transparent fill. By default it's false and 20.0% opacity.
            imageComparison.setDifferenceRectangleFilling(true, 0.0);
            imageComparison.isFillDifferenceRectangles();
            imageComparison.getPercentOpacityDifferenceRectangles();
    
            //ExcludedRectangleFilling - Fill the inside the excluded rectangles with a transparent fill. By default it's false and 20.0% opacity.
            imageComparison.setExcludedRectangleFilling(true, 0.0);
            imageComparison.isFillExcludedRectangles();
            imageComparison.getPercentOpacityExcludedRectangles();
    
            //Destination. Before comparing also can be added destination file for result image.
            imageComparison.setDestination(resultDestination);
            imageComparison.getDestination();
    
            //MaximalRectangleCount - It means that would get first x biggest rectangles for drawing.
            // by default all the rectangles would be drawn.
            imageComparison.setMaximalRectangleCount(3);
            imageComparison.getMaximalRectangleCount();
    
            //MinimalRectangleSize - The number of the minimal rectangle size. Count as (width x height).
            // by default it's 1.
            imageComparison.setMinimalRectangleSize(1);
            imageComparison.getMinimalRectangleSize();

            //Change the level of the pixel tolerance:
            imageComparison.setPixelToleranceLevel(0.1);
            imageComparison.getPixelToleranceLevel();
    
            //After configuring the ImageComparison object, can be executed compare() method:
            ImageComparisonResult imageComparisonResult = imageComparison.compareImages();
    
            
            //And Result Image
            BufferedImage resultImage = imageComparisonResult.getResult();
           
    
            //Image can be saved after comparison, using ImageComparisonUtil.
            ImageComparisonUtil.saveImage(resultDestination, resultImage); 
		}else {
			System.out.println("Not found -->"+s);
		}
		}
	}
}
