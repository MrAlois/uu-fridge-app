import React from 'react';
import Gallery from './Gallery';

interface ListingDetail {
    id: string;
    name: string;
    image: string[];
    createdAt: Date;
    donorName: string;
    donorEmail: string;
    donorMobile: string;
    expiryDate: Date;
    pickupLocation: string;
    dietaryProperties: string;
    description: string;
}

export default function FoodListingDetailView() {
    // Mockup data for demo
    const listing: ListingDetail = {
        id: '1',
        name: 'Lorem ipsum',
        image: ['https://via.placeholder.com/360', 'https://via.placeholder.com/360', 'https://via.placeholder.com/360'],
        createdAt: new Date(),
        donorName: 'John Doe',
        donorEmail: 'john@example.com',
        donorMobile: '123-456-7890',
        expiryDate: new Date(),
        pickupLocation: '123 Main St, Anytown, USA',
        dietaryProperties: 'Vegan, Gluten-free',
        description: 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Pellentesque sapien. In enim a arcu imperdiet malesuada. Donec vitae arcu. Nulla est. Nullam justo enim, consectetuer nec, ullamcorper ac, vestibulum in, elit. Duis viverra diam non justo. Nullam feugiat, turpis at pulvinar vulputate, erat libero tristique tellus, nec bibendum odio risus sit amet ante. Nam sed tellus id magna elementum tincidunt. Etiam quis quam. Mauris metus. Fusce nibh. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus, omnis voluptas assumenda est, omnis dolor repellendus.'
    };

    return (
        <div className="p-4">
            <div className="flex justify-between items-start border-b pb-3 mb-3">
                <h1 className="text-2xl overflow-wrap">{listing.name}</h1>
                <div className="text-xs text-gray-500">{listing.createdAt.toDateString()}</div>
            </div>

            <Gallery images={listing.image} />

            <div className="py-2">
                <h2 className="font-bold mb-2">Donor Information:</h2>
                <p>Name: {listing.donorName}</p>
                <p>Email: {listing.donorEmail}</p>
                <p>Mobile: {listing.donorMobile}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Expires On:</h2>
                <p>{listing.expiryDate.toDateString()}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Pickup Location:</h2>
                <p>{listing.pickupLocation}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Dietary Properties:</h2>
                <p>{listing.dietaryProperties}</p>
            </div>

            <div className="py-2">
                <h2 className="font-bold mb-2">Description:</h2>
                <p>{listing.description}</p>
            </div>
        </div>
    );
};