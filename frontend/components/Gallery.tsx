import {useMemo, useState} from 'react';

interface GalleryProps {
    images?: (string | undefined)[]
}

const Gallery = ({images = []} : GalleryProps) => {
    const placeholderImage = "https://via.placeholder.com/360";
    const [selectedIndex, setSelectedIndex] = useState(0);
    const processedImages = useMemo(() => {
        if (images && images.length > 0) {
            return images.map(image => image ? image : placeholderImage)
        } else {
            return [placeholderImage];
        }
    }, [images]);

    const handlePreviousClick = () => {
        setSelectedIndex((oldIndex) => oldIndex - 1);
    };

    const handleNextClick = () => {
        setSelectedIndex((oldIndex) => oldIndex + 1);
    };

    return (
        <div className="relative group h-60 md:h-96 flex justify-center items-center overflow-hidden">
            <img
                src={processedImages[selectedIndex]}
                alt="gallery-image"
                className="w-auto h-full mx-auto object-cover object-center"
                onError={(e) => {
                    (e.target as HTMLImageElement).src = "https://via.placeholder.com/360";
                }}
            />
            <div
                className="absolute top-1/2 -translate-y-1/2 left-4 opacity-0 group-hover:opacity-100 transition-opacity">
                <button
                    onClick={handlePreviousClick}
                    disabled={selectedIndex === 0}
                    className="p-2 bg-white bg-opacity-60 rounded-full"
                >
                    Previous
                </button>
            </div>

            <div
                className="absolute top-1/2 -translate-y-1/2 right-4 opacity-0 group-hover:opacity-100 transition-opacity">
                <button
                    onClick={handleNextClick}
                    disabled={selectedIndex === images.length - 1}
                    className="p-2 bg-white bg-opacity-60 rounded-full"
                >
                    Next
                </button>
            </div>
        </div>
    );
};

export default Gallery;