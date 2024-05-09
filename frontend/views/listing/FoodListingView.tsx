import React from "react";
import Gallery from "Frontend/views/listing/Gallery";
import {NavLink} from "react-router-dom";

interface Listing {
    id: string;
    name: string;
    images: string[];
    description: string;
    donor: string;
    createdAt: Date;
    expiryDate: Date;
    address: string;
}

export default function FoodListingView() {
    const listings: Listing[] = Array.from({ length: 10 }, (_, i) => ({
        id: `${i}`,
        name: 'Lorem ipsum dolor sit amet',
        images: ['https://via.placeholder.com/360', 'https://via.placeholder.com/360'],
        description: 'Consectetur adipiscing elit',
        donor: `Donor ${i}`,
        createdAt: new Date(),
        expiryDate: new Date(),
        address: '123 Main St, Anytown, USA',
    }));

    const truncate = (input: string) => input.length > 20 ? `${input.substring(0, 20)}...` : input;

    return (
        <div className="mx-auto px-4 sm:px-0 w-full">

            <input
                className="w-full lg:max-w-2xl p-2 mt-6 mb-4 border-gray-300 rounded-md shadow-sm focus:ring-indigo-500 focus:border-indigo-500"
                type="text"
                placeholder="Search Listings..."
            />

            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-5 gap-4 justify-items-center">
                {listings.map((listing: Listing) => (
                    <div key={listing.id}
                         className="flex flex-col border overflow-hidden rounded-md items-stretch w-full max-w-xs p-4">
                        <div className="flex justify-between">
                            <NavLink to={`/food-listings/${listing.id}`}>
                                <h4 className="font-semibold">{truncate(listing.name)}</h4>
                            </NavLink>
                            <p className="text-sm text-gray-500">{listing.createdAt.toDateString()}</p>
                        </div>
                        <Gallery images={listing.images}/>
                        <div className="text-sm">
                            <p className="mb-2"><strong>Donor:</strong> {listing.donor}</p>
                            <p className="mb-2"><strong>Expires on:</strong> {listing.expiryDate.toDateString()}</p>
                            <p className="mb-2"><strong>Address:</strong> {listing.address}</p>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}