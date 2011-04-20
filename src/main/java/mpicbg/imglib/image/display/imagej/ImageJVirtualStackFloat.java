package mpicbg.imglib.image.display.imagej;

import ij.ImagePlus;
import mpicbg.imglib.RandomAccessibleInterval;
import mpicbg.imglib.converter.Converter;
import mpicbg.imglib.type.Type;
import mpicbg.imglib.type.numeric.real.FloatType;
import mpicbg.imglib.view.RandomAccessibleZeroMinIntervalCursor;

public class ImageJVirtualStackFloat< S extends Type< S > & Comparable< S > > extends ImageJVirtualStack< S, FloatType >
{
	public ImageJVirtualStackFloat( RandomAccessibleInterval< S > source, Converter< S, FloatType > converter )
	{
		super( source, converter, new FloatType(), ImagePlus.GRAY32 );
		setMinMax( source, converter );
	}
	
	public void setMinMax ( RandomAccessibleInterval< S > source, Converter< S, FloatType > converter )
	{		
		RandomAccessibleZeroMinIntervalCursor< S > cursor = new RandomAccessibleZeroMinIntervalCursor< S >( source );
		if ( cursor.hasNext() ) {
			S s = cursor.next();
			S min = s.copy();
			S max = s.copy(); 
			while ( cursor.hasNext() ) {
				s = cursor.next();
				if ( s.compareTo( min ) < 0 )
				{
					min.set( s );
				}
				if ( s.compareTo( max ) > 0 )
				{
					max.set( s );
				}
			}
			FloatType t = new FloatType();
			converter.convert( min, t );
			final double fmin = t.getRealDouble();
			converter.convert( max, t );
			final double fmax = t.getRealDouble();
			
			System.out.println("fmax = " + fmax );
			System.out.println("fmin = " + fmin );
			imageProcessor.setMinAndMax( fmin, fmax );
		}
	}
}
