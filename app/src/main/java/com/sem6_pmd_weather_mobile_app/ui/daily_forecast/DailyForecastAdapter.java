package com.sem6_pmd_weather_mobile_app.ui.daily_forecast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sem6_pmd_weather_mobile_app.R;
import com.sem6_pmd_weather_mobile_app.models.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder>{
    private List<DailyForecast> dailyForecasts = new ArrayList<>();

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
        this.notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView weatherImage;
        private final TextView date;
        private final TextView maxTemp;
        private final TextView minTemp;
        private final TextView rainChance;

        public ImageView getWeatherImage() {
            return weatherImage;
        }

        public TextView getDate() {
            return date;
        }

        public TextView getMaxTemp() {
            return maxTemp;
        }

        public TextView getMinTemp() {
            return minTemp;
        }

        public TextView getRainChance() {
            return rainChance;
        }

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            weatherImage = view.findViewById(R.id.weather_image_day);
            date = view.findViewById(R.id.weather_date);
            maxTemp = view.findViewById(R.id.max_temperature_day);
            minTemp = view.findViewById(R.id.min_temperature_day);
            rainChance = view.findViewById(R.id.rain_chance_day);
        }
    }

    public DailyForecastAdapter() {
        super();
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.forecast_day_item, viewGroup, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.getWeatherImage().setImageDrawable(dailyForecasts.get(position).weatherImage);
        viewHolder.getDate().setText(dailyForecasts.get(position).date);
        viewHolder.getMaxTemp().setText(dailyForecasts.get(position).maxTemperature);
        viewHolder.getMinTemp().setText(dailyForecasts.get(position).minTemperature);
        viewHolder.getRainChance().setText(dailyForecasts.get(position).rainChance);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dailyForecasts.size();
    }
}
