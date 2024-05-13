import {useEffect, useState} from 'react';

interface GalleryProps {
    images?: (string | undefined)[]
}

const Gallery = ({images = ['https://via.placeholder.com/360']} : GalleryProps) => {
    const [selectedIndex, setSelectedIndex] = useState(0);

    useEffect(() => {
        if (images) {
            images = images.filter((image): image is string => image !== undefined);
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
                src={images[selectedIndex]}
                alt="selected"
                className="w-auto h-full mx-auto object-cover object-center"
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