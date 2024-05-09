import { useState } from 'react';

interface GalleryProps {
    images: string[]
}

const Gallery = ({images} : GalleryProps) => {
    const [selectedIndex, setSelectedIndex] = useState(0);

    const handlePreviousClick = () => {
        setSelectedIndex((oldIndex) => oldIndex - 1);
    };

    const handleNextClick = () => {
        setSelectedIndex((oldIndex) => oldIndex + 1);
    };

    return (
        <div className="relative group">
            <img
                src={images[selectedIndex]}
                alt="selected"
                className="object-cover w-full max-h-60 md:max-h-96"
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